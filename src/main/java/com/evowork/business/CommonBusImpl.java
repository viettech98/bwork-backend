
package com.evowork.business;

import com.evowork.config.AppConstant;
import com.evowork.entity.AppVersion;
import com.evowork.entity.MobileLog;
import com.evowork.entity.User;
import com.evowork.enumeration.AppType;
import com.evowork.enumeration.PlatformType;
import com.evowork.exception.BusinessException;
import com.evowork.repository.CommonRepository;
import com.evowork.repository.MobileLogRepository;
import com.evowork.utility.DateUtility;
import com.evowork.utility.StringUtility;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.common.ExceptionFilter;
import com.evowork.viewmodel.common.UpdateVersionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

/**
 * Common bus
 *
 * @since 1.0
 */
@Component
public class CommonBusImpl extends AbstractBusiness implements CommonBus {

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    MobileLogRepository mobileLogRepo;

    @Override
    public UpdateVersionModel getUpdateVersion(String platform, String buildId) throws BusinessException {
        if (StringUtility.isNotEmpty(platform)) {
            AppVersion versions;
            if (buildId.equals(AppConstant.APP_ADMIN)) {
                versions = commonRepository.getUpdateVersion(platform, AppType.APP_ADMIN.getValue());
            } else {
                versions = commonRepository.getUpdateVersion(platform, AppType.APP_USER.getValue());
            }
            if (versions != null) {
                UpdateVersionModel model = new UpdateVersionModel(versions);
                return model;
            }
        }
        return null;
    }

    @Override
    public Boolean saveException(ExceptionFilter filter, UserInfo userInfo, String platform) throws BusinessException {
        initialize();
        if (filter != null) {
            MobileLog mobileLog = new MobileLog();
            mobileLog.setClassName(filter.getClassName());
            mobileLog.setException(filter.getException());
            if (StringUtility.isNotEmpty(platform) && platform.equals("android")) {
                mobileLog.setOsType(PlatformType.ANDROID);
            } else {
                mobileLog.setOsType(PlatformType.IOS);
            }
            if (userInfo != null) {
                Long userId = userInfo.getUserId();
                Optional<User> user = userRepo.findById(userId);
                if (user.isPresent()) {
                    mobileLog.setCreatedBy(user.get());
                }
            }
            mobileLog.setCreatedAt(sysTimestamp);
            mobileLog.setOsVersion(filter.getOsVersion());
            mobileLog.setAppVersion(filter.getAppVersion());
            mobileLogRepo.save(mobileLog);
            return true;
        }
        return false;
    }

    @Override
    public Timestamp getTimeToday() throws BusinessException {
        initialize();
        return sysTimestamp;
    }

    /**
     * Upload file
     *
     * @param file
     * @param pathSave
     * @return
     */
    public String uploadFile(MultipartFile file, String pathSave, String idAlias) {
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String filePath = pathSave + "/" +
                    DateUtility.formatDate(new Date(), DateUtility.FORMAT_YEAR)
                    + DateUtility.formatDate(new Date(), DateUtility.FORMAT_MONTH)
                    + "/" +
                    StringUtility.generateRandomString(30, Optional.of(false)) + "." + extension;
            Path path = Paths.get(appConfig.getStaticResourcePhysicalPath() + "/" + idAlias + "/" + filePath);
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            Files.write(path, bytes);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}