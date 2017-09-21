package com.example.biro.ptf.Contract;

/**
 * Created by Biro on 6/30/2017.
 */

public class Constants {

    public static final String accessToken = "access_token";
    public static final String isUserApproved = "AppIsUserApproved";

    public static final class Register{

        public static final String url = "http://mkarim-001-site1.etempurl.com/api/accounts/Register?";
        public static final String parmKeyProvider = "provider";
        public static final String parmKeyOrganizationCode = "organizationCode";
        public static final String parmAccessToken = "externalAccessToken";
        public static final String parmMobile = "mobile";
        public static final String parmName = "name";
        public static final String organizationCodeParm = "PTF";
        public static final String providerParam = "facebook";


    }
    public static final class ObtainAccessToken {


        public static final String url = "http://mkarim-001-site1.etempurl.com/api/accounts/ObtainLocalAccessToken";
        public static final String providerKey = "provider";
        public static final String organizationCodeParm = "PTF";
        public static final String organizationCodeKey = "organizationCode";
        public static final String externalAccessTokenKey = "externalAccessToken";
        public static final String providerParam = "facebook";

        public static final String[] requestKeys = {providerKey, organizationCodeKey, externalAccessTokenKey};


    }


    public static final class userKeys {
        public static final String key = "USER";
        public static final String url = "http://mkarim-001-site1.etempurl.com/api/Users/Put";
        public static final String id = "id";
        public static final String name = "name";
        public static final String email = "email";
        public static final String gender = "gender";
        public static final String link = "link";
        public static final String birthdate = "birthdate";
        public static final String cityId = "cityId";
        public static final String address = "address";
        public static final String area = "area";
        public static final String iosDeviceId = "iosDeviceId";
        public static final String completeProfile = "completeProfile";
        public static final String mobile = "mobile";
        public static final String androidDeviceId = "androidDeviceId";
        public static final String[] reqestKeys = {id, name, mobile,
                androidDeviceId,
                iosDeviceId,
                completeProfile, cityId, address, gender, birthdate, area};

    }
    public static final class getLoggedUser{
        public static final String url = "http://mkarim-001-site1.etempurl.com/api/Users/GetLoggedInUser";
            public static final String []requestKeys={"Authorization"};
    }
    public static final class getMeeting{
        public static final String url = "http://mkarim-001-site1.etempurl.com/api/meetings/Get";
        public static final String []requestKeys={"Authorization"};

    }
}
