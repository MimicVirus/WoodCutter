package config;

public class Config {
    private static final Config config = new Config();
    private boolean isBurning = false;
    private String status = "Starting script";
    private boolean isRunning = false;
    private Config() {
    } //
    public static Config getConfig() {
        return config;
    }

    public boolean isScriptRunning() {
        return isRunning;
    }

    public void setScriptRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean getIsBurning() {
        return isBurning;
    }

    public void setIsBurning(boolean isBurning) {
        this.isBurning = isBurning;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}