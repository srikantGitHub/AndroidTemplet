package com.srikant.templet.authentication;
import android.content.ContentValues;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import com.srikant.templet.BR;

/**
 * Created by Udini on 6/26/13.
 */
public class User extends BaseObservable implements Serializable{
    private static final long serialVersionUID = 1L;

    @Bindable String  id;                 @Bindable String  uuid;       @Bindable String  name;
    @Bindable String  email;              @Bindable String  dob;        @Bindable String  mobile_no;
    @Bindable String  mobile_no_device;   @Bindable String  imei;       @Bindable String  address;
    @Bindable String  country_id;         @Bindable String  state_id;   @Bindable String  district_id;
    @Bindable String  city_id;            @Bindable String  pin_code;   @Bindable String  landmark;
    @Bindable String  sim_no;             @Bindable String  gender;     @Bindable String  dt_created;
    @Bindable String  dt_update;          @Bindable String  image_path; @Bindable ContentValues contentValues;
    private String access_token;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
       notifyPropertyChanged(BR.id);
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        notifyPropertyChanged(BR.uuid);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
        notifyPropertyChanged(BR.dob);
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
        notifyPropertyChanged(BR.mobile_no);
    }

    public String getMobile_no_device() {
        return mobile_no_device;
    }

    public void setMobile_no_device(String mobile_no_device) {
        this.mobile_no_device = mobile_no_device;
        notifyPropertyChanged(BR.mobile_no_device);
    }
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
        notifyPropertyChanged(BR.imei);
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
        notifyPropertyChanged(BR.country_id);
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
        notifyPropertyChanged( BR.state_id);
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
        notifyPropertyChanged(BR.district_id);
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
       notifyPropertyChanged(BR.city_id);
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
       notifyPropertyChanged(BR.pin_code);
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
       notifyPropertyChanged(BR.landmark);
    }

    public String getSim_no() {
        return sim_no;
    }

    public void setSim_no(String sim_no) {
        this.sim_no = sim_no;
       notifyPropertyChanged(BR.sim_no);
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
       notifyPropertyChanged(BR.gender);
    }

    public String getDt_created() {
        return dt_created;
    }

    public void setDt_created(String dt_created) {
        this.dt_created = dt_created;
       notifyPropertyChanged(BR.dt_created);
    }
    public String getDt_update() {
        return dt_update;
    }

    public void setDt_update(String dt_update) {
        this.dt_update = dt_update;
       notifyPropertyChanged(BR.dt_update);
    }
    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
       notifyPropertyChanged(BR.image_path);
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues=new ContentValues();
        try{
            contentValues.put("uuid", getUuid());
            contentValues.put("name",getName());
            contentValues.put("mobile_no",getMobile_no());
            contentValues.put("email",getEmail());
            contentValues.put("dob",getDob());
            contentValues.put("address",getAddress());
            contentValues.put("landmark",getLandmark());
            contentValues.put("state_id",getState_id());
            contentValues.put("district_id",getDistrict_id());
            contentValues.put("pin_code",getPin_code());
            contentValues.put("imei",getImei());
            contentValues.put("mobile_device",getMobile_no_device());
            contentValues.put("sim_no",getSim_no());
            contentValues.put("city_id", getCity_id());
            contentValues.put("image_path",getImage_path());
            contentValues.put("gender", getGender());
        }catch(Exception e){}
        return contentValues;
    }
    public String createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/Profile");

        if (!direct.exists()) {
            direct.mkdirs();
        }
        File file = new File(direct, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.toString();
    }
}
