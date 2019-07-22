package com.impax.nakathisacco.Database;
/** Defines sqlite database structure(tables and column names) for this project   **/
public class DBStucture {


        public static String DM_NAME = "NakathiDB";
        public static int DB_VERSION = 1;


/** Members table   **/

    public static String TABLE_MEMBERS= "members";
    public static String MEMBER_ID         = "member_id";
    public static String MEMBER_FIRST_NAME = "member_first_name";
    public static String MEMBER_LAST_NAME  = "member_last_name";
    public static String MEMBER_SURNAME= "member_surname";
    public static String MEMBER_ID_NUMBER= "member_id_number";
    public static String MEMBER_PHONE_NUMBER= "member_phone_number";
    public static String MEMBER_NEXT_OF_KIN= "member_next_of_kin";
    public static String MEMBER_PHONE_NUMBER_NEXT_OF_KIN= "phone_number_next_of_kin";
    public static String MEMBER_EMAIL= "member_email";
    public static String MEMBER_USERNAME= "member_username";
    public static String MEMBER_PASSWORD= "member_password";
    public static String MEMBER_TYPE_ID= "member_type_id";/* Foreign key linking member_type table */


}
