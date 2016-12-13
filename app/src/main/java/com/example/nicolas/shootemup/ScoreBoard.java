package com.example.nicolas.shootemup;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nicolas on 12/11/2016.
 */

public class ScoreBoard implements Parcelable {

    private long id;
    private String name;
    private long score;
    private int coin;
    private int shootSpeed;
    private int shipSpeed;
    private TypeWeapon weaponType;

    public ScoreBoard() {
        super();
    }

    public ScoreBoard(long id, String name, long score, int coin, int shootSpeed, int shipSpeed, TypeWeapon weaponType) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.coin = coin;
        this.shootSpeed = shootSpeed;
        this.shipSpeed = shipSpeed;
        this.weaponType = weaponType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getShootSpeed() {
        return shootSpeed;
    }

    public void setShootSpeed(int shootSpeed) {
        this.shootSpeed = shootSpeed;
    }

    public int getShipSpeed() {
        return shipSpeed;
    }

    public void setShipSpeed(int shipSpeed) {
        this.shipSpeed = shipSpeed;
    }

    public TypeWeapon getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(TypeWeapon weaponType) {
        this.weaponType = weaponType;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(name);
        out.writeLong(score);
        out.writeInt(coin);
        out.writeInt(shootSpeed);
        out.writeInt(shipSpeed);
        out.writeString(weaponType.name());

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<ScoreBoard> CREATOR = new Parcelable.Creator<ScoreBoard>() {
           public ScoreBoard createFromParcel(Parcel in) {
            return new ScoreBoard(in);
        }

        public ScoreBoard[] newArray(int size) {
            return new ScoreBoard[size];
        }
    };


    private ScoreBoard(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.score = in.readLong();
        this.coin = in.readInt();
        this.shootSpeed = in.readInt();
        this.shipSpeed = in.readInt();
        String weaponTypeName = in.readString();

        switch (weaponTypeName){
            case "BASE":
                this.weaponType = TypeWeapon.BASE;
                break;
            case "BAZOOKA":
                this.weaponType = TypeWeapon.BAZOOKA;
                break;
            case "GATTELING":
                this.weaponType = TypeWeapon.GATTELING;
                break;
        }
    }

}
