package org.example.resources;

import java.io.Serializable;

public class Resources implements Serializable {
    private static boolean mute = false;
    private static SpriteSheet wallPic;
    private static SpriteSheet gatePic;
    private static SpriteSheet marioPic[] = new SpriteSheet[10];
    private static SpriteSheet megaMarioPic[] = new SpriteSheet[10];
    private static SpriteSheet fireMarioPic[] = new SpriteSheet[10];
    private static  SpriteSheet mushroomPic;
    private static SpriteSheet koopaPic[] = new SpriteSheet[6];
    private static SpriteSheet powerUp;
    private static SpriteSheet coinPic;
    private static SpriteSheet circleFire;
    private static SpriteSheet fireBall;
    private static SpriteSheet oneCoinBlock;
    private static SpriteSheet multiCoinBlock;
    private static SpriteSheet emptyBlock;
    private static SpriteSheet flowerPic;
    private static SpriteSheet starPic;
    private static SpriteSheet goomba;
    private static SpriteSheet spiny;
    private static SpriteSheet kingKoopaRF;
    private static SpriteSheet kingKoopaLF;
    private static SpriteSheet fire;
    private static SpriteSheet kingKoopaLeft;
    private static SpriteSheet kingKoopaRight;
    private static SpriteSheet like;
    private static SpriteSheet dislike;
    private static SpriteSheet heart;
    private static SpriteSheet laugh;
    private static SpriteSheet angry;
    private static SpriteSheet[] punchingBowser = new SpriteSheet[6];
    private static Sound endOfLevel;
    private static Sound endOfGame;
    private static Sound hurtMario;
    private static Sound killEnemy;
    private static Sound themeSong;
    private static Sound bossFight;
    private static Sound[] sounds = new Sound[6];
    public static void init(){
        initPictures();
        initSounds();
    }
    public static void initPictures(){
        marioPic[0] = new SpriteSheet("Mario_Standing.gif");
        marioPic[1] = new SpriteSheet("Mario_Walk_1.gif");
        marioPic[2] = new SpriteSheet("Mario_Walk_2.gif");
        marioPic[3] = new SpriteSheet("Mario_Walk_3.gif");
        marioPic[4] = new SpriteSheet("Mario_Standing.gif");
        marioPic[5] = new SpriteSheet("Mario_StandingL.gif");
        marioPic[6] = new SpriteSheet("Mario_Walk_3L.gif");
        marioPic[7] = new SpriteSheet("Mario_Walk_2L.gif");
        marioPic[8] = new SpriteSheet("Mario_Walk_1L.gif");
        marioPic[9] = new SpriteSheet("Mario_StandingL.gif");

        megaMarioPic[0] = new SpriteSheet("img_11.png");
        megaMarioPic[1] = new SpriteSheet("img_8.png");
        megaMarioPic[2] = new SpriteSheet("img_9.png");
        megaMarioPic[3] = new SpriteSheet("img_10.png");
        megaMarioPic[4] = new SpriteSheet("img_11.png");
        megaMarioPic[5] = new SpriteSheet("img_11L.png");
        megaMarioPic[6] = new SpriteSheet("img_10L.png");
        megaMarioPic[7] = new SpriteSheet("img_9L.png");
        megaMarioPic[8] = new SpriteSheet("img_8L.png");
        megaMarioPic[9] = new SpriteSheet("img_11L.png");

        fireMarioPic[0] = new SpriteSheet("img_7.png");
        fireMarioPic[1] = new SpriteSheet("img_4.png");
        fireMarioPic[2] = new SpriteSheet("img_5.png");
        fireMarioPic[3] = new SpriteSheet("img_6.png");
        fireMarioPic[4] = new SpriteSheet("img_7.png");
        fireMarioPic[5] = new SpriteSheet("img_7L.png");
        fireMarioPic[6] = new SpriteSheet("img_6L.png");
        fireMarioPic[7] = new SpriteSheet("img_5L.png");
        fireMarioPic[8] = new SpriteSheet("img_4L.png");
        fireMarioPic[9] = new SpriteSheet("img_7L.png");

        koopaPic[0] = new SpriteSheet("Green_Koopa_TroopaR.gif");
        koopaPic[1] = new SpriteSheet("Green_Koopa_Troopa2R.gif");
        koopaPic[2] = new SpriteSheet("Green_Koopa_TroopaR.gif");
        koopaPic[3] = new SpriteSheet("Green_Koopa_Troopa.gif");
        koopaPic[4] = new SpriteSheet("Green_Koopa_Troopa2.gif");
        koopaPic[5] = new SpriteSheet("Green_Koopa_Troopa.gif");

        wallPic = new SpriteSheet("Bricks.gif");
        oneCoinBlock = new SpriteSheet("oneCoinBlock.png");
        multiCoinBlock = new SpriteSheet("multiCoinBlock.png");
        emptyBlock = new SpriteSheet("EmptyBlock.jpg");
        powerUp = new SpriteSheet("Question_Block.gif");

        coinPic = new SpriteSheet("Coin.gif");
        circleFire = new SpriteSheet("star5.PNG");
        fireBall = new SpriteSheet("p_fireball1.png");
        flowerPic = new SpriteSheet("flower.jpg");
        starPic = new SpriteSheet("star.jpg");

        mushroomPic = new SpriteSheet("mushroom1.jpg");
        goomba = new SpriteSheet("Goomba.gif");
        spiny = new SpriteSheet("spiny.jpg");
        gatePic = new SpriteSheet("Castle.gif");

        kingKoopaLeft = new SpriteSheet("bowser.jpg");
        kingKoopaRight = new SpriteSheet("bowserR.jpg");
        kingKoopaRF = new SpriteSheet("bowserRF.jpg");
        kingKoopaLF = new SpriteSheet("bowserLF.jpg");
        fire = new SpriteSheet("fire.jpg");

        punchingBowser[0] = new SpriteSheet("punchingBowser0R.png");
        punchingBowser[1] = new SpriteSheet("punchingBowser1R.png");
        punchingBowser[2] = new SpriteSheet("punchingBowser2R.png");
        punchingBowser[3] = new SpriteSheet("punchingBowser0L.png");
        punchingBowser[4] = new SpriteSheet("punchingBowser1L.png");
        punchingBowser[5] = new SpriteSheet("punchingBowser2L.png");

        like = new SpriteSheet("like.jpg");
        dislike = new SpriteSheet("dislike.jpg");
        heart = new SpriteSheet("heart.jpg");
        laugh = new SpriteSheet("laugh.jpg");
        angry = new SpriteSheet("angry.png");
    }
    public static void initSounds(){
        endOfLevel = new Sound("D:\\github\\mario-plus-plus-plus\\untitled\\src\\main\\java\\org\\example\\resources\\sound\\endOfLevel.wav" );
        sounds[0] = endOfLevel;
        endOfGame = new Sound("D:\\github\\mario-plus-plus-plus\\untitled\\src\\main\\java\\org\\example\\resources\\sound\\endOfGame.wav" );
        sounds[1] = endOfGame;
        hurtMario = new Sound("D:\\github\\mario-plus-plus-plus\\untitled\\src\\main\\java\\org\\example\\resources\\sound\\hurtMario.wav" );
        sounds[2] = hurtMario;
        killEnemy = new Sound("D:\\github\\mario-plus-plus-plus\\untitled\\src\\main\\java\\org\\example\\resources\\sound\\killEnemy.wav" );
        sounds[3] = killEnemy;
        themeSong = new Sound("D:\\github\\mario-plus-plus-plus\\untitled\\src\\main\\java\\org\\example\\resources\\sound\\themeSong.wav" );
        sounds[4] = themeSong;
        bossFight = new Sound("D:\\github\\mario-plus-plus-plus\\untitled\\src\\main\\java\\org\\example\\resources\\sound\\bossFight.wav" );
        sounds[5] = bossFight;
    }
    public static void stopSounds(){
        for (int i = 0; i < 6; i++) {
            Sound sound = Resources.getSounds()[i];
            if(sound != null) {
                sound.stop();
            }
        }
    }

    public static SpriteSheet[] getFireMarioPic() {
        return fireMarioPic;
    }
    public static SpriteSheet getCoinPic() {
        return coinPic;
    }
    public static SpriteSheet getCircleFire() {
        return circleFire;
    }
    public static SpriteSheet getFireBall() {
        return fireBall;
    }
    public static SpriteSheet getEmptyBlock() {
        return emptyBlock;
    }
    public static SpriteSheet getFire() {
        return fire;
    }
    public static Sound getEndOfLevel() {
        return endOfLevel;
    }
    public static Sound getEndOfGame() {
        return endOfGame;
    }
    public static Sound getBossFight() {
        return bossFight;
    }
    public static SpriteSheet getWallPic() {
        return wallPic;
    }
    public static SpriteSheet getGatePic() {
        return gatePic;
    }
    public static SpriteSheet[] getMarioPic() {
        return marioPic;
    }
    public static SpriteSheet[] getMegaMarioPic() {
        return megaMarioPic;
    }
    public static SpriteSheet getMushroomPic() {
        return mushroomPic;
    }
    public static SpriteSheet[] getKoopaPic() {
        return koopaPic;
    }
    public static SpriteSheet getPowerUp() {
        return powerUp;
    }
    public static SpriteSheet getOneCoinBlock() {
        return oneCoinBlock;
    }
    public static SpriteSheet getMultiCoinBlock() {
        return multiCoinBlock;
    }
    public static SpriteSheet getFlowerPic() {
        return flowerPic;
    }
    public static SpriteSheet getStarPic() {
        return starPic;
    }
    public static SpriteSheet getGoomba() {
        return goomba;
    }
    public static SpriteSheet getSpiny() {
        return spiny;
    }
    public static SpriteSheet getKingKoopaRF() {
        return kingKoopaRF;
    }
    public static SpriteSheet getKingKoopaLF() {
        return kingKoopaLF;
    }
    public static SpriteSheet getKingKoopaLeft() {
        return kingKoopaLeft;
    }
    public static SpriteSheet getKingKoopaRight() {
        return kingKoopaRight;
    }

    public static SpriteSheet getLike() {
        return like;
    }

    public static SpriteSheet getDislike() {
        return dislike;
    }

    public static SpriteSheet getHeart() {
        return heart;
    }

    public static SpriteSheet getLaugh() {
        return laugh;
    }

    public static SpriteSheet getAngry() {
        return angry;
    }
    public static Sound getHurtMario() {
        return hurtMario;
    }
    public static Sound getKillEnemy() {
        return killEnemy;
    }
    public static Sound getThemeSong() {
        return themeSong;
    }
    public static Sound[] getSounds() {
        return sounds;
    }
    public static boolean isMute() {
        return mute;
    }
    public static void setMute(boolean mute) {
        Resources.mute = mute;
    }
    public static SpriteSheet[] getPunchingBowser() {
        return punchingBowser;
    }
}
