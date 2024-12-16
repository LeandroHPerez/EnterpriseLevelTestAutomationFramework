package com.leandroperez.taf.core.session;

import com.leandroperez.taf.core.enumerator.PlatformInTest;
import com.leandroperez.taf.core.enumerator.TestExecutionStrategy;
import com.leandroperez.taf.utils.threadutil.ThreadUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Leandro Henrique Perez
 */

@Setter
@Slf4j
public class Session {
    private String PROJECT_ROOT_PATH = System.getProperty("user.dir");
    private String UITEST_PROPERTIES_FILE_PATH = PROJECT_ROOT_PATH + "/src/test/java/com/leandroperez/taf/config/uitest.properties";
    Path uiTestPropertiesPath = Paths.get(UITEST_PROPERTIES_FILE_PATH);
    private WebDriver webDriver;
    private AppiumDriver appiumDriver;
    private AndroidDriver androidDriver;
    private IOSDriver iosDriver;
    private PlatformInTest currentPlatformInTest = null;
    private HashMap<String, String> customProperties = new HashMap<>();
    Reader reader;
    String decodedPath;
    private boolean debugMode = false;
    SessionTimeStorage sessionTimeStorage = new SessionTimeStorage();

    //Instance initializer block
    {
        try {
            decodedPath = URLDecoder.decode(uiTestPropertiesPath.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            reader = new FileReader(decodedPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();
        try {
            prop.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadOnlyCustomPropertiesNotNoneAndNotNull(prop);
    }


    public void loadAllCustomProperties(Properties propLoaded) {
        propLoaded.forEach((k, v) -> customProperties.put(k.toString(), v.toString()));
    }

    public void loadOnlyCustomPropertiesNotNoneAndNotNull(Properties propLoaded) {
        //DEBUG
        System.out.println("*** debugMode in Session is: " + debugMode + " ***\n Set debugMode to true to see more data about properties loaded from file");

        propLoaded.forEach((k, v) -> {
            if (v != null && !"none".equalsIgnoreCase(v.toString()) && !"null".equalsIgnoreCase(v.toString())) {
                customProperties.put(k.toString(), v.toString());
                if (debugMode) {
                    System.out.println("key: " + k + "value: " + v);
                }
            }
        });
    }

    public void startSession(PlatformInTest platformInTest, TestExecutionStrategy testExecutionStrategy) throws Exception {
        if (testExecutionStrategy == null) {
            throw new RuntimeException("Session configuration error, testExecutionStrategy is null");
        }
        if (platformInTest == null) {
            throw new RuntimeException("Session configuration error, platformInTest is null");
        }

        currentPlatformInTest = platformInTest;

        switch (testExecutionStrategy) {
            case LOCAL:
                System.out.println("Starting LOCAL session");
                startLocalSession(platformInTest);
                break;
            case GRID:
                System.out.println("Starting GRID session");
                startGridSession();
                break;
            case REMOTE:
                System.out.println("Starting REMOTE session");
                startRemoteSession(getURLFromConfiguratedLocalMobileHubAddressProperty(), getConfiguratedMobileDesiredCapabilities(), platformInTest);
                break;
        }
    }

    private void startDefaultLocalSession(PlatformInTest platformInTest) {
        try {
            if (platformInTest == PlatformInTest.WEB) {
                //todo
            } else if (platformInTest == PlatformInTest.ANDROID) {
                executeRuntimeCommandTxtFile();
                AppiumDriverLocalService service = getAppiumDriverDefaultLocalService();
                service.start();
                this.androidDriver = new AndroidDriver(service, getConfiguratedMobileDesiredCapabilities());
                this.appiumDriver = this.androidDriver;
            } else if (platformInTest == PlatformInTest.IOS) {
                executeRuntimeCommandTxtFile();
                AppiumDriverLocalService service = getAppiumDriverDefaultLocalService();
                this.iosDriver = new IOSDriver(service, getConfiguratedMobileDesiredCapabilities());
                this.appiumDriver = this.iosDriver;
            } else {
                throw new IllegalArgumentException("Error: platformInTest is invalid");
            }
        } catch (Exception e) {
            System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass().getEnclosingMethod().getName()
                    + "\n" + "Error: " + e);
        }
    }

    private void startLocalSession(PlatformInTest platformInTest) {
        if (platformInTest == null) {
            throw new RuntimeException("Session configuration error, platformInTest is null");
        }
        sessionTimeStorage.setDefaultValuesForSessionInPlatform(platformInTest); //default times for each platform session

        try {
            StringBuilder urlString = new StringBuilder();
            switch (platformInTest) {
                case WEB:
                    urlString.append(customProperties.get("localWebHubAddress"));
                    break;
                case ANDROID:
                    executeRuntimeCommandTxtFile();
                    AppiumDriverLocalService serviceAndroid = getAppiumDriverDefaultLocalService();
                    serviceAndroid.start();
                    DesiredCapabilities desiredCapabilitiesAndroid = getConfiguratedMobileDesiredCapabilities();
                    this.androidDriver = new AndroidDriver(serviceAndroid, desiredCapabilitiesAndroid);
                    this.appiumDriver = this.androidDriver;
                    break;
                case IOS:
                    executeRuntimeCommandTxtFile();
                    ThreadUtil.sleepSafe(sessionTimeStorage.timeToSleepStep01);
                    AppiumDriverLocalService serviceIOS = getAppiumDriverDefaultLocalService();
                    ThreadUtil.sleepSafe(sessionTimeStorage.timeToSleepStep02);
                    serviceIOS.start();
                    DesiredCapabilities desiredCapabilitiesIOS = getConfiguratedMobileDesiredCapabilities();
                    ThreadUtil.sleepSafe(sessionTimeStorage.timeToSleepStep03);
                    this.iosDriver = new IOSDriver(serviceIOS, desiredCapabilitiesIOS);
                    this.appiumDriver = this.iosDriver;
                    ThreadUtil.sleepSafe(sessionTimeStorage.timeToSleepStep04);
                    break;
            }
        } catch (Exception e) {
            System.out.println(this.getClass().getSimpleName() + "." + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()\n" + "Error: " + e);
        }
    }


    private void startRemoteSession(URL url, final DesiredCapabilities desiredCapabilities, PlatformInTest platformInTest) {
        if (url == null || desiredCapabilities == null || platformInTest == null) {
            throw new RuntimeException("Session configuration error.");
        }
        try {
            StringBuilder urlString = new StringBuilder();
            switch (platformInTest) {
                case WEB:
                    urlString.append(customProperties.get("localWebHubAddress"));
                    RemoteWebDriver remoteWebDriver = new RemoteWebDriver(url, desiredCapabilities);
                    this.webDriver = remoteWebDriver;
                    this.webDriver.manage().window().maximize();
                    break;
                case ANDROID:
                    this.androidDriver = new AndroidDriver(url, desiredCapabilities);
                    this.appiumDriver = this.androidDriver;
                    break;
                case IOS:
                    this.iosDriver = new IOSDriver(url, desiredCapabilities);
                    this.appiumDriver = this.iosDriver;
                    break;
            }
        } catch (Exception e) {
            System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass().getEnclosingMethod().getName()
                    + "\n" + "Error: " + e);
        }


        if (Boolean.parseBoolean(customProperties.get("isMobile"))) { //nao existe essa config. remover
            if (platformInTest == PlatformInTest.ANDROID) {
                if (customProperties.get("useRemoteServiceUrlForAndroid").equals("yes")) {
                    this.androidDriver = new AndroidDriver(url, desiredCapabilities);
                    this.appiumDriver = this.androidDriver;
                } else {
                    executeRuntimeCommandTxtFile();
                    AppiumDriverLocalService service = getAppiumDriverDefaultLocalService();
                    service.start();

                    this.androidDriver = new AndroidDriver(service, desiredCapabilities);
                    this.appiumDriver = this.androidDriver;
                }
            } else if (platformInTest == PlatformInTest.IOS) {
                if (customProperties.get("useRemoteServiceUrlForiOS").equals("yes")) {
                    this.iosDriver = new IOSDriver(url, desiredCapabilities);
                    this.appiumDriver = this.iosDriver;
                } else {
                    executeRuntimeCommandTxtFile();
                    AppiumDriverLocalService service = getAppiumDriverDefaultLocalService();


                    this.iosDriver = new IOSDriver(service, desiredCapabilities);
                    this.appiumDriver = this.iosDriver;
                }
                this.webDriver = this.appiumDriver;
            } else {
                RemoteWebDriver remoteWebDriver = new RemoteWebDriver(url, desiredCapabilities);
                this.webDriver = remoteWebDriver;
                this.webDriver.manage().window().maximize();
            }
        }
    }

    private URL getURLFromConfiguratedLocalMobileHubAddressProperty() {
        StringBuilder urlString = new StringBuilder();
        URL url = null;
        try {
            urlString.append(customProperties.get("localMobileHubAddress"));
            url = new URL(urlString.toString());
        } catch (MalformedURLException mue) {
            System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass().getEnclosingMethod().getName()
                    + "\n" + "Error: " + mue);
        }
        return url;
    }


    private static AppiumDriverLocalService getAppiumDriverDefaultLocalService() {
        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
        return service;
    }

    private static AppiumDriverLocalService getAppiumDriverDefaultLocalServiceWithLog(File testLogFilePath) {
        if (testLogFilePath == null)
            testLogFilePath = new File("./log/appium_log.txt");
        AppiumDriverLocalService service = new AppiumServiceBuilder().withLogFile(testLogFilePath).build();
        service.start();
        return service;
    }

    private void startGridSession() {
        /* TODO implement startGridSession */
    }

    private boolean isNotNullOrEmptyOrNoneValue(String string) {
        if (string == null)
            return false;
        return !(string.equalsIgnoreCase("none") && string.isEmpty());
    }

    private boolean isNullOrEmptyOrNoneValue(String string) {
        if (string == null)
            return true;
        return (string.equalsIgnoreCase("none") || string.isEmpty());
    }


    public DesiredCapabilities getConfiguratedMobileDesiredCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        if (isAndroid()) {
            String platformName = customProperties.get("platformName");
            String automationName = customProperties.get("automationName");
            String platformVersion = customProperties.get("platformVersion");
            String deviceName = customProperties.get("deviceName");
            String androidNoReset = customProperties.get("androidNoReset");
            String androidFullReset = customProperties.get("androidFullReset");
            String appPackage = customProperties.get("appPackage");
            String appActivity = customProperties.get("appActivity");
            String app = customProperties.get("app");

            if (isNullOrEmptyOrNoneValue(platformName) || isNullOrEmptyOrNoneValue(automationName)) {
                throw new IllegalArgumentException("platformName and automationName are required.");
            }

            if (isNotNullOrEmptyOrNoneValue(platformName)) {
                desiredCapabilities.setCapability("platformName", platformName);
            }

            if (isNotNullOrEmptyOrNoneValue(automationName)) {
                desiredCapabilities.setCapability("appium:automationName", automationName);
            }

            if (isNotNullOrEmptyOrNoneValue(platformVersion)) {
                desiredCapabilities.setCapability("appium:platformVersion", platformVersion);
            }

            if (isNotNullOrEmptyOrNoneValue(deviceName)) {
                desiredCapabilities.setCapability("appium:deviceName", deviceName);
            }

            if (isNotNullOrEmptyOrNoneValue(androidNoReset)) {
                desiredCapabilities.setCapability("appium:noReset", androidNoReset);
            }

            if (isNotNullOrEmptyOrNoneValue(androidFullReset)) {
                desiredCapabilities.setCapability("appium:fullReset", androidFullReset);
            }

            if (isNotNullOrEmptyOrNoneValue(appPackage)) {
                desiredCapabilities.setCapability("appium:appPackage", appPackage);
            }

            if (isNotNullOrEmptyOrNoneValue(appActivity)) {
                desiredCapabilities.setCapability("appium:appActivity", appActivity);
            }

            if (isNotNullOrEmptyOrNoneValue(app)) {
                desiredCapabilities.setCapability("appium:app", app);
            }

        }
        if (isIOS()) {
            String iosLocalizationBool = customProperties.get("iosLocalization");
            String appLanguage = customProperties.get("iosAppLanguage");
            String appLocale = customProperties.get("iosAppLocale");


            String platformName = customProperties.get("iosPlatformName");
            String automationName = customProperties.get("iosAutomationName");
            String iosBundleId = customProperties.get("iosBundleId");
            String platformVersion = customProperties.get("iosPlatformVersion");
            String deviceName = customProperties.get("iosDeviceName");
            String iosApp = customProperties.get("iosApp");
            String iosNoReset = customProperties.get("iosNoReset");
            String iosFullReset = customProperties.get("iosFullReset");
            String udid = customProperties.get("iosUdid");
            String xcodeOrgId = customProperties.get("iosXcodeOrgId");
            String xcodeSigningId = customProperties.get("iosXcodeSigningId");
            String iosShowIOSLogBool = customProperties.get("iosShowIOSLog");

            if (isNullOrEmptyOrNoneValue(platformName) || isNullOrEmptyOrNoneValue(automationName) || isNullOrEmptyOrNoneValue(iosBundleId)) {
                throw new IllegalArgumentException("iosPlatformName and iosAutomationName are required. iosBundleId");
            }

            if (isNullOrEmptyOrNoneValue(iosApp) && isNullOrEmptyOrNoneValue(iosBundleId)) {
                throw new IllegalArgumentException("At least one of the 'iosApp' or 'iosBundleId' fild value must be entered.");
            }

            if (isNotNullOrEmptyOrNoneValue(platformName)) {
                desiredCapabilities.setCapability("platformName", platformName);
            }

            if (isNotNullOrEmptyOrNoneValue(automationName)) {
                desiredCapabilities.setCapability("appium:automationName", automationName);
            }

            if (isNotNullOrEmptyOrNoneValue(iosBundleId)) {
                desiredCapabilities.setCapability("appium:bundleId", iosBundleId);
            }

            if (isNotNullOrEmptyOrNoneValue(platformVersion)) {
                desiredCapabilities.setCapability("appium:platformVersion", platformVersion);
            }

            if (isNotNullOrEmptyOrNoneValue(deviceName)) {
                desiredCapabilities.setCapability("appium:deviceName", deviceName);
            }

            if (isNotNullOrEmptyOrNoneValue(iosNoReset)) {
                desiredCapabilities.setCapability("appium:noReset", iosNoReset);
            }

            if (isNotNullOrEmptyOrNoneValue(iosFullReset)) {
                desiredCapabilities.setCapability("appium:fullReset", iosFullReset);
            }


            if (isNotNullOrEmptyOrNoneValue(udid)) {
                desiredCapabilities.setCapability("appium:udid", udid);
            }

            if (isNotNullOrEmptyOrNoneValue(xcodeOrgId)) {
                desiredCapabilities.setCapability("appium:xcodeOrgId", xcodeOrgId);
            }

            if (isNotNullOrEmptyOrNoneValue(xcodeSigningId)) {
                desiredCapabilities.setCapability("appium:xcodeSigningId", xcodeSigningId);
            }

            if (isNotNullOrEmptyOrNoneValue(iosShowIOSLogBool)) {
                desiredCapabilities.setCapability("appium:showIOSLog", iosShowIOSLogBool);
            }

            if (isNotNullOrEmptyOrNoneValue(iosApp)) {
                desiredCapabilities.setCapability("appium:app", iosApp);
            }

            //location implementation for iOS
            if (isNotNullOrEmptyOrNoneValue(iosLocalizationBool)) {
                if (iosLocalizationBool.equalsIgnoreCase("yes")) {
                    System.out.println("iosLocalization is enabled");
                    System.out.println("iosAppLanguage value " + appLanguage);
                    System.out.println("iosAppLocale value " + appLocale);

                    if (isNotNullOrEmptyOrNoneValue(appLanguage)) {
                        desiredCapabilities.setCapability("appium:language", appLanguage);
                    }

                    if (isNotNullOrEmptyOrNoneValue(appLocale)) {
                        desiredCapabilities.setCapability("appium:locale", appLocale);
                    }
                }
            }
        }

        if (customProperties.get("browserName").equals("chrome")) {
            ChromeOptions chromeOptions = getChromeOptions();
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        }
        if (customProperties.get("browserName").equals("firefox")) {
            FirefoxOptions firefoxOptions = getFireFoxOptions();
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, firefoxOptions);
        }

        //DEBUG
        System.out.println("*** debugMode in Session is: " + debugMode + " ***\n Set debugMode to true to see more data about Desired capabilities loaded");
        if (debugMode) {
            System.out.println("DesiredCapabilities loaded:\n" + desiredCapabilities);
        }

        return desiredCapabilities;
    }


    public DesiredCapabilities getDesiredCapabilitiesSeeTest() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        if (Boolean.parseBoolean(customProperties.get("isAndroid"))) {
            desiredCapabilities.setCapability("accessKey", customProperties.get("accessKey"));
            desiredCapabilities.setCapability("testName", customProperties.get("testNameAndroid"));
            desiredCapabilities.setCapability("deviceQuery", customProperties.get("deviceQueryAndroid"));
            desiredCapabilities.setCapability("app", customProperties.get("cloudAppAndroid"));
            desiredCapabilities.setCapability("appPackage", customProperties.get("appPackage"));
            desiredCapabilities.setCapability("appActivity", customProperties.get("appActivity"));
        }
        if (Boolean.parseBoolean(customProperties.get("isIos"))) {
            desiredCapabilities.setCapability("accessKey", customProperties.get("accessKey"));
            desiredCapabilities.setCapability("testName", customProperties.get("testNameIos"));
            desiredCapabilities.setCapability("deviceQuery", customProperties.get("deviceQueryIos"));
            desiredCapabilities.setCapability("appVersion", customProperties.get("appVersionIos"));
            desiredCapabilities.setCapability("platformName", customProperties.get("iosPlatformName"));
            desiredCapabilities.setCapability("newCommandTimeout", customProperties.get("newCommandTimeout"));
            desiredCapabilities.setCapability("automationName", customProperties.get("iosAutomationName"));
            desiredCapabilities.setCapability("app", customProperties.get("cloudAppIos"));
            desiredCapabilities.setCapability("bundleId", customProperties.get("iosBundleId"));
        }
        return desiredCapabilities;
    }

    public Boolean isMobile() {
        return (appiumDriver != null || androidDriver != null || iosDriver != null);
    }

    public Boolean isAndroid() {
        return (currentPlatformInTest != null && currentPlatformInTest == PlatformInTest.ANDROID);
    }

    public Boolean isIOS() {
        return (currentPlatformInTest != null && currentPlatformInTest == PlatformInTest.IOS);
    }

    public Boolean isWeb() {
        return (currentPlatformInTest != null && currentPlatformInTest == PlatformInTest.WEB);
    }

    public AppiumDriver getAppiumDriver() {
        return this.appiumDriver;
    }

    public AndroidDriver getAndroidDriver() {
        return this.androidDriver;
    }

    public IOSDriver getIosDriver() {
        return this.iosDriver;
    }

    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    public WebDriver setWebDriverTimeout(long time, TimeUnit timeUnit) {
        if (webDriver != null)
            getWebDriver().manage().timeouts().pageLoadTimeout(time, timeUnit);
        return this.webDriver;
    }


    public void quitWebDriverSession() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }

    public void quitMobileDriverSession() {
        if (appiumDriver != null) {
            appiumDriver.quit();
            androidDriver = null;
            iosDriver = null;
        }
    }




    /*
        protected ChromeOptions getChromeOptions() {
            ChromeOptions chromeOptions = new ChromeOptions();
            return chromeOptions;
        }

        protected FirefoxOptions getFireFoxOptions() {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            return firefoxOptions;
        }
    */


    public HashMap<String, String> getCustomProperties() {
        return customProperties;
    }

    public void closeSession() {

        //TODO rever isso
        /*
        try {


            if (customProperties.get("isAndroid").equals("true")) {
                //appiumDriver.resetApp();
            }else if (!appiumDriver.removeApp(customProperties.get("iosBundleId"))) {
                    //appiumDriver.resetApp();
                }
        } catch (Exception e) {
            log.error("Error closing App");
            e.printStackTrace();
        }
        */

        if (appiumDriver != null) {
            try {
                appiumDriver.quit();
                androidDriver = null;
                iosDriver = null;
                //TODO verificar inclusão de código para service.stop() para fechar a sessão Appium
            } catch (Exception e) {
                log.error("Error closing session for appiumDriver");
                e.printStackTrace();
            }
        }

        if (webDriver != null) {
            try {
                getWebDriver().quit();
            } catch (Exception e) {
                log.error("Error closing session for webDriver");
                e.printStackTrace();
            }
        }

    }

    private void executeRuntimeCommandTxtFile() {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                Runtime.getRuntime().exec("./command.txt");
            } else if (SystemUtils.IS_OS_MAC) {
                Runtime.getRuntime().exec("chmod -R 777 " + "./command.txt");
            } else {
                Runtime.getRuntime().exec("./command.txt");
            }
        } catch (IOException e) {
            log.error("Error on execute executeCommandTxtFile()");
            e.printStackTrace();
        }
    }


    private AppiumDriverLocalService startAppiumServerOnSupportedOsWithCustomBuilder() {
        AppiumDriverLocalService service = null;
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                //Runtime.getRuntime().exec("./command.txt");
                //todo implementar no Windows
            } else if (SystemUtils.IS_OS_MAC) {
                String nodePath = "/usr/local/bin/node";
                String appiumPath = "/opt/homebrew/lib/node_modules/appium/build/lib/main.js";

                File fileNodePath = new File(FilenameUtils.normalize(nodePath));
                File fileAppiumPath = new File(FilenameUtils.normalize(appiumPath));


                AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
                serviceBuilder.withIPAddress("http://127.0.0.1"); //Appium_ip_address
                serviceBuilder.usingPort(4723);

                //serviceBuilder.usingDriverExecutable(fileNodePath); //node path
                //serviceBuilder.withAppiumJS(fileAppiumPath); //appium path installed globally via npm. check path with npm root -g in macOS terminal

                service = new AppiumServiceBuilder()
                        .withAppiumJS(new File("/usr/local/bin/node"))
                        .withIPAddress("127.0.0.1")
                        .usingPort(4723)
                        .build();

                service.isRunning();
                //service.start();
            } else {
                //Runtime.getRuntime().exec("./command.txt");
            }
        } catch (Exception e) {
            log.error("Error on execute startAppiumServer()");
            e.printStackTrace();
        }
        return service;
    }

    protected ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        return chromeOptions;
    }

    protected FirefoxOptions getFireFoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        return firefoxOptions;
    }


    public PlatformInTest getCurrentPlatformInTest() {
        return currentPlatformInTest;
    }
}