package org.example.server.model;

public class ServerSetting {
    private int shopDiamondConversionRate = 10;
    private double shopLevelMultiplier;
    private double speedPotionMultiplier;
    private int speedPotionPeriod;
    private int invisibilityPotionPeriod;
    private int healthPotionHPPercent;
    private double damageBombBlockArea;
    private int damageBombPercent;
    private double speedBombBlockArea;
    private double speedBombMultiplier;
    private int hammerStunPeriod;
    private double swordBlockRange;
    private int swordPercentDamage;
    private double swordPushBackBlock;
    private int swordCoolDown;
    private double marathonMultiplierSpeed;
    private double marathonMultiplierSlowDown;
    private int marathonPeriodSlowDown;
    private double marathonLifeTimeMultiplier;
    private double marathonDistanceMultiplier;
    private int marathonMinLifeTime;
    private int marathonMinDistance;
    private double survivalDamageMultiplier;
    private double survivalEquipmentMultiplier;

    public ServerSetting() {
    }

    public ServerSetting(int shopDiamondConversionRate, double shopLevelMultiplier, double speedPotionMultiplier,
                         int speedPotionPeriod, int invisibilityPotionPeriod, int healthPotionHPPercent,
                         double damageBombBlockArea, int damageBombPercent, double speedBombBlockArea,
                         double speedBombMultiplier, int hammerStunPeriod, double swordBlockRange, int swordPercentDamage,
                         double swordPushBackBlock, int swordCoolDown, double marathonMultiplierSpeed,
                         double marathonMultiplierSlowDown, int marathonPeriodSlowDown, double marathonLifeTimeMultiplier,
                         double marathonDistanceMultiplier, int marathonMinLifeTime, int marathonMinDistance,
                         double survivalDamageMultiplier, double survivalEquipmentMultiplier) {
        this.shopDiamondConversionRate = shopDiamondConversionRate;
        this.shopLevelMultiplier = shopLevelMultiplier;
        this.speedPotionMultiplier = speedPotionMultiplier;
        this.speedPotionPeriod = speedPotionPeriod;
        this.invisibilityPotionPeriod = invisibilityPotionPeriod;
        this.healthPotionHPPercent = healthPotionHPPercent;
        this.damageBombBlockArea = damageBombBlockArea;
        this.damageBombPercent = damageBombPercent;
        this.speedBombBlockArea = speedBombBlockArea;
        this.speedBombMultiplier = speedBombMultiplier;
        this.hammerStunPeriod = hammerStunPeriod;
        this.swordBlockRange = swordBlockRange;
        this.swordPercentDamage = swordPercentDamage;
        this.swordPushBackBlock = swordPushBackBlock;
        this.swordCoolDown = swordCoolDown;
        this.marathonMultiplierSpeed = marathonMultiplierSpeed;
        this.marathonMultiplierSlowDown = marathonMultiplierSlowDown;
        this.marathonPeriodSlowDown = marathonPeriodSlowDown;
        this.marathonLifeTimeMultiplier = marathonLifeTimeMultiplier;
        this.marathonDistanceMultiplier = marathonDistanceMultiplier;
        this.marathonMinLifeTime = marathonMinLifeTime;
        this.marathonMinDistance = marathonMinDistance;
        this.survivalDamageMultiplier = survivalDamageMultiplier;
        this.survivalEquipmentMultiplier = survivalEquipmentMultiplier;
    }

    public int getShopDiamondConversionRate() {
        return shopDiamondConversionRate;
    }

    public void setShopDiamondConversionRate(int shopDiamondConversionRate) {
        this.shopDiamondConversionRate = shopDiamondConversionRate;
    }

    public double getShopLevelMultiplier() {
        return shopLevelMultiplier;
    }

    public void setShopLevelMultiplier(double shopLevelMultiplier) {
        this.shopLevelMultiplier = shopLevelMultiplier;
    }

    public double getDamageBombBlockArea() {
        return damageBombBlockArea;
    }

    public void setDamageBombBlockArea(double damageBombBlockArea) {
        this.damageBombBlockArea = damageBombBlockArea;
    }

    public int getDamageBombPercent() {
        return damageBombPercent;
    }

    public void setDamageBombPercent(int damageBombPercent) {
        this.damageBombPercent = damageBombPercent;
    }

    public int getHammerStunPeriod() {
        return hammerStunPeriod;
    }

    public void setHammerStunPeriod(int hammerStunPeriod) {
        this.hammerStunPeriod = hammerStunPeriod;
    }

    public double getSpeedPotionMultiplier() {
        return speedPotionMultiplier;
    }

    public void setSpeedPotionMultiplier(double speedPotionMultiplier) {
        this.speedPotionMultiplier = speedPotionMultiplier;
    }

    public int getSpeedPotionPeriod() {
        return speedPotionPeriod;
    }

    public void setSpeedPotionPeriod(int speedPotionPeriod) {
        this.speedPotionPeriod = speedPotionPeriod;
    }

    public int getInvisibilityPotionPeriod() {
        return invisibilityPotionPeriod;
    }

    public void setInvisibilityPotionPeriod(int invisibilityPotionPeriod) {
        this.invisibilityPotionPeriod = invisibilityPotionPeriod;
    }

    public int getHealthPotionHPPercent() {
        return healthPotionHPPercent;
    }

    public void setHealthPotionHPPercent(int healthPotionHPPercent) {
        this.healthPotionHPPercent = healthPotionHPPercent;
    }

    public double getSpeedBombBlockArea() {
        return speedBombBlockArea;
    }

    public void setSpeedBombBlockArea(double speedBombBlockArea) {
        this.speedBombBlockArea = speedBombBlockArea;
    }

    public double getSpeedBombMultiplier() {
        return speedBombMultiplier;
    }

    public void setSpeedBombMultiplier(double speedBombMultiplier) {
        this.speedBombMultiplier = speedBombMultiplier;
    }

    public double getSwordBlockRange() {
        return swordBlockRange;
    }

    public void setSwordBlockRange(double swordBlockRange) {
        this.swordBlockRange = swordBlockRange;
    }

    public int getSwordPercentDamage() {
        return swordPercentDamage;
    }

    public void setSwordPercentDamage(int swordPercentDamage) {
        this.swordPercentDamage = swordPercentDamage;
    }

    public double getSwordPushBackBlock() {
        return swordPushBackBlock;
    }

    public void setSwordPushBackBlock(double swordPushBackBlock) {
        this.swordPushBackBlock = swordPushBackBlock;
    }

    public int getSwordCoolDown() {
        return swordCoolDown;
    }

    public void setSwordCoolDown(int swordCoolDown) {
        this.swordCoolDown = swordCoolDown;
    }

    public double getMarathonMultiplierSpeed() {
        return marathonMultiplierSpeed;
    }

    public void setMarathonMultiplierSpeed(double marathonMultiplierSpeed) {
        this.marathonMultiplierSpeed = marathonMultiplierSpeed;
    }

    public double getMarathonMultiplierSlowDown() {
        return marathonMultiplierSlowDown;
    }

    public void setMarathonMultiplierSlowDown(double marathonMultiplierSlowDown) {
        this.marathonMultiplierSlowDown = marathonMultiplierSlowDown;
    }

    public int getMarathonPeriodSlowDown() {
        return marathonPeriodSlowDown;
    }

    public void setMarathonPeriodSlowDown(int marathonPeriodSlowDown) {
        this.marathonPeriodSlowDown = marathonPeriodSlowDown;
    }

    public double getMarathonLifeTimeMultiplier() {
        return marathonLifeTimeMultiplier;
    }

    public void setMarathonLifeTimeMultiplier(double marathonLifeTimeMultiplier) {
        this.marathonLifeTimeMultiplier = marathonLifeTimeMultiplier;
    }

    public double getMarathonDistanceMultiplier() {
        return marathonDistanceMultiplier;
    }

    public void setMarathonDistanceMultiplier(double marathonDistanceMultiplier) {
        this.marathonDistanceMultiplier = marathonDistanceMultiplier;
    }

    public int getMarathonMinLifeTime() {
        return marathonMinLifeTime;
    }

    public void setMarathonMinLifeTime(int marathonMinLifeTime) {
        this.marathonMinLifeTime = marathonMinLifeTime;
    }

    public int getMarathonMinDistance() {
        return marathonMinDistance;
    }

    public void setMarathonMinDistance(int marathonMinDistance) {
        this.marathonMinDistance = marathonMinDistance;
    }

    public double getSurvivalDamageMultiplier() {
        return survivalDamageMultiplier;
    }

    public void setSurvivalDamageMultiplier(double survivalDamageMultiplier) {
        this.survivalDamageMultiplier = survivalDamageMultiplier;
    }

    public double getSurvivalEquipmentMultiplier() {
        return survivalEquipmentMultiplier;
    }

    public void setSurvivalEquipmentMultiplier(double survivalEquipmentMultiplier) {
        this.survivalEquipmentMultiplier = survivalEquipmentMultiplier;
    }
}
