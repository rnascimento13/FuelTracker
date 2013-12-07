package com.brmobsoft.fueltracker;

import android.content.Context;

/**
 * Created by Rodrigo on 18/07/13.
 */
public class CurrencyHandler {

    private static final String TAG = CurrencyHandler.class.getSimpleName();
    private static String curency;
    private Context context;
    public static final String C_FUELADD_ODOMETER = "odometer";

    public CurrencyHandler(Context context){
        this.context = context;
    }
    public String findCurrency(String c){
        CurrencyEnum.valueOf(c.toString()).changeCurrency();
//        MyApp.getContext().getString(R.string.BRL_Currency_name);
        return curency;
    }
    public enum CurrencyEnum {
        AED { @Override void changeCurrency() { curency = context.getString(R.string.AED_Currency_name); } },
        AFA { @Override void changeCurrency() { curency = context.getString(R.string.AFA_Currency_name); } },
        ALL { @Override void changeCurrency() { curency = context.getString(R.string.ALL_Currency_name); } },
        AMD { @Override void changeCurrency() { curency = context.getString(R.string.AMD_Currency_name); } },
        ANG { @Override void changeCurrency() { curency = context.getString(R.string.ANG_Currency_name); } },
        AOA { @Override void changeCurrency() { curency = context.getString(R.string.AOA_Currency_name); } },
        ARS { @Override void changeCurrency() { curency = context.getString(R.string.ARS_Currency_name); } },
        AUD { @Override void changeCurrency() { curency = context.getString(R.string.AUD_Currency_name); } },
        AWG { @Override void changeCurrency() { curency = context.getString(R.string.AWG_Currency_name); } },
        AZM { @Override void changeCurrency() { curency = context.getString(R.string.AZM_Currency_name); } },
        BAM { @Override void changeCurrency() { curency = context.getString(R.string.BAM_Currency_name); } },
        BBD { @Override void changeCurrency() { curency = context.getString(R.string.BBD_Currency_name); } },
        BDT { @Override void changeCurrency() { curency = context.getString(R.string.BDT_Currency_name); } },
        BGL { @Override void changeCurrency() { curency = context.getString(R.string.BGL_Currency_name); } },
        BHD { @Override void changeCurrency() { curency = context.getString(R.string.BHD_Currency_name); } },
        BIF { @Override void changeCurrency() { curency = context.getString(R.string.BIF_Currency_name); } },
        BMD { @Override void changeCurrency() { curency = context.getString(R.string.BMD_Currency_name); } },
        BND { @Override void changeCurrency() { curency = context.getString(R.string.BND_Currency_name); } },
        BOB { @Override void changeCurrency() { curency = context.getString(R.string.BOB_Currency_name); } },
        BRL { @Override void changeCurrency() { curency = context.getString(R.string.BRL_Currency_name); } },
        BSD { @Override void changeCurrency() { curency = context.getString(R.string.BSD_Currency_name); } },
        BTN { @Override void changeCurrency() { curency = context.getString(R.string.BTN_Currency_name); } },
        BWP { @Override void changeCurrency() { curency = context.getString(R.string.BWP_Currency_name); } },
        BYR { @Override void changeCurrency() { curency = context.getString(R.string.BYR_Currency_name); } },
        BZD { @Override void changeCurrency() { curency = context.getString(R.string.BZD_Currency_name); } },
        CAD { @Override void changeCurrency() { curency = context.getString(R.string.CAD_Currency_name); } },
        CDF { @Override void changeCurrency() { curency = context.getString(R.string.CDF_Currency_name); } },
        CHF { @Override void changeCurrency() { curency = context.getString(R.string.CHF_Currency_name); } },
        CLP { @Override void changeCurrency() { curency = context.getString(R.string.CLP_Currency_name); } },
        CNY { @Override void changeCurrency() { curency = context.getString(R.string.CNY_Currency_name); } },
        COP { @Override void changeCurrency() { curency = context.getString(R.string.COP_Currency_name); } },
        CRC { @Override void changeCurrency() { curency = context.getString(R.string.CRC_Currency_name); } },
        CUP { @Override void changeCurrency() { curency = context.getString(R.string.CUP_Currency_name); } },
        CVE { @Override void changeCurrency() { curency = context.getString(R.string.CVE_Currency_name); } },
        CYP { @Override void changeCurrency() { curency = context.getString(R.string.CYP_Currency_name); } },
        DJF { @Override void changeCurrency() { curency = context.getString(R.string.DJF_Currency_name); } },
        DKK { @Override void changeCurrency() { curency = context.getString(R.string.DKK_Currency_name); } },
        DOP { @Override void changeCurrency() { curency = context.getString(R.string.DOP_Currency_name); } },
        DZD { @Override void changeCurrency() { curency = context.getString(R.string.DZD_Currency_name); } },
        EEK { @Override void changeCurrency() { curency = context.getString(R.string.EEK_Currency_name); } },
        EGP { @Override void changeCurrency() { curency = context.getString(R.string.EGP_Currency_name); } },
        ERN { @Override void changeCurrency() { curency = context.getString(R.string.ERN_Currency_name); } },
        ETB { @Override void changeCurrency() { curency = context.getString(R.string.ETB_Currency_name); } },
        EUR { @Override void changeCurrency() { curency = context.getString(R.string.EUR_Currency_name); } },
        FJD { @Override void changeCurrency() { curency = context.getString(R.string.FJD_Currency_name); } },
        FKP { @Override void changeCurrency() { curency = context.getString(R.string.FKP_Currency_name); } },
        GBP { @Override void changeCurrency() { curency = context.getString(R.string.GBP_Currency_name); } },
        GEL { @Override void changeCurrency() { curency = context.getString(R.string.GEL_Currency_name); } },
        GGP { @Override void changeCurrency() { curency = context.getString(R.string.GGP_Currency_name); } },
        USD { @Override void changeCurrency() { curency = context.getString(R.string.USD_Currency_name); } };

        /**
        <string name="FKP_Currency_name" >@string/GBP_Currency_name</string>
        <string name="GBP_Currency_name" >Pounds</string>
        <string name="GEL_Currency_name" >Lari</string>
        <string name="GGP_Currency_name" >@string/GBP_Currency_name</string>
        <string name="GHC_Currency_name" >Cedis</string>
        <string name="GIP_Currency_name" >@string/GBP_Currency_name</string>
        <string name="GMD_Currency_name" >Dalasi</string>
        <string name="GNF_Currency_name" >@string/CHF_Currency_name</string>
        <string name="GTQ_Currency_name" >Quetzales</string>
        <string name="GYD_Currency_name" >@string/USD_Currency_name</string>
        <string name="HKD_Currency_name" >@string/USD_Currency_name</string>
        <string name="HNL_Currency_name" >Lempiras</string>
        <string name="HRK_Currency_name" >Kuna</string>
        <string name="HTG_Currency_name" >Gourdes</string>
        <string name="HUF_Currency_name" >Forint</string>
        <string name="IDR_Currency_name" >Rupiahs</string>
        <string name="ILS_Currency_name" >Shekels</string>
        <string name="IMP_Currency_name" >@string/GBP_Currency_name</string>
        <string name="INR_Currency_name" >Rupees</string>
        <string name="IQD_Currency_name" >@string/DZD_Currency_name</string>
        <string name="IRR_Currency_name" >Rials</string>
        <string name="ISK_Currency_name" >Kronur</string>
        <string name="JEP_Currency_name" >@string/GBP_Currency_name</string>
        <string name="JMD_Currency_name" >@string/USD_Currency_name</string>
        <string name="JOD_Currency_name" >@string/DZD_Currency_name</string>
        <string name="JPY_Currency_name" >Yen</string>
        <string name="KES_Currency_name" >Shillings</string>
        <string name="KGS_Currency_name" >Soms</string>
        <string name="KHR_Currency_name" >Riels</string>
        <string name="KMF_Currency_name" >@string/CHF_Currency_name</string>
        <string name="KPW_Currency_name" >Won</string>
        <string name="KRW_Currency_name" >Won</string>
        <string name="KWD_Currency_name" >@string/DZD_Currency_name</string>
        <string name="KYD_Currency_name" >@string/USD_Currency_name</string>
        <string name="KZT_Currency_name" >Tenge</string>
        <string name="LAK_Currency_name" >Kips</string>
        <string name="LBP_Currency_name" >@string/GBP_Currency_name</string>
        <string name="LKR_Currency_name" >@string/INR_Currency_name</string>
        <string name="LRD_Currency_name" >@string/USD_Currency_name</string>
        <string name="LSL_Currency_name" >Maloti</string>
        <string name="LTL_Currency_name" >Litai</string>
        <string name="LVL_Currency_name" >Lati</string>
        <string name="LYD_Currency_name" >@string/DZD_Currency_name</string>
        <string name="MAD_Currency_name" >Dirhams</string>
        <string name="MDL_Currency_name" >Lei</string>
        <string name="MGF_Currency_name" >@string/CHF_Currency_name</string>
        <string name="MKD_Currency_name" >Denars</string>
        <string name="MNT_Currency_name" >Tugriks</string>
        <string name="MMK_Currency_name" >Kyats</string>
        <string name="MOP_Currency_name" >Patacas</string>
        <string name="MRO_Currency_name" >Ouguiyas</string>
        <string name="MTL_Currency_name" >Liri</string>
        <string name="MUR_Currency_name" >@string/INR_Currency_name</string>
        <string name="MVR_Currency_name" >Rufiyaa</string>
        <string name="MWK_Currency_name" >Kwachas</string>
        <string name="MXN_Currency_name" >@string/COP_Currency_name</string>
        <string name="MYR_Currency_name" >Ringgits</string>
        <string name="MZM_Currency_name" >Meticais</string>
        <string name="NAD_Currency_name" >@string/USD_Currency_name</string>
        <string name="NGN_Currency_name" >Nairas</string>
        <string name="NIO_Currency_name" >Cordobas</string>
        <string name="NOK_Currency_name" >Kroner</string>
        <string name="NPR_Currency_name" >@string/INR_Currency_name</string>
        <string name="NZD_Currency_name" >@string/USD_Currency_name</string>
        <string name="OMR_Currency_name" >@string/IRR_Currency_name</string>
        <string name="PAB_Currency_name" >Balboa</string>
        <string name="PEN_Currency_name" >Soles</string>
        <string name="PGK_Currency_name" >Kina</string>
        <string name="PHP_Currency_name" >@string/COP_Currency_name</string>
        <string name="PKR_Currency_name" >@string/INR_Currency_name</string>
        <string name="PLN_Currency_name" >Zlotych</string>
        <string name="PYG_Currency_name" >Guarani</string>
        <string name="QAR_Currency_name" >@string/IRR_Currency_name</string>
        <string name="ROL_Currency_name" >@string/MDL_Currency_name</string>
        <string name="RUR_Currency_name" >@string/BYR_Currency_name</string>
        <string name="RWF_Currency_name" >@string/CHF_Currency_name</string>
        <string name="SAR_Currency_name" >Riyals</string>
        <string name="SBD_Currency_name" >@string/USD_Currency_name</string>
        <string name="SCR_Currency_name" >@string/INR_Currency_name</string>
        <string name="SDD_Currency_name" >@string/DZD_Currency_name</string>
        <string name="SEK_Currency_name" >Kronor</string>
        <string name="SGD_Currency_name" >@string/USD_Currency_name</string>
        <string name="SHP_Currency_name" >@string/GBP_Currency_name</string>
        <string name="SIT_Currency_name" >Tolars</string>
        <string name="SKK_Currency_name" >Koruny</string>
        <string name="SLL_Currency_name" >Leones</string>
        <string name="SPL_Currency_name" >Luigini</string>
        <string name="SOS_Currency_name" >@string/KES_Currency_name</string>
        <string name="SRG_Currency_name" >@string/AWG_Currency_name</string>
        <string name="STD_Currency_name" >Dobras</string>
        <string name="SVC_Currency_name" >@string/CRC_Currency_name</string>
        <string name="SYP_Currency_name" >@string/GBP_Currency_name</string>
        <string name="SZL_Currency_name" >Emalangeni</string>
        <string name="THB_Currency_name" >Baht</string>
        <string name="TJS_Currency_name" >Somoni</string>
        <string name="TMM_Currency_name" >@string/AZM_Currency_name</string>
        <string name="TND_Currency_name" >@string/DZD_Currency_name</string>
        <string name="TOP_Currency_name" >Pa'anga</string>
        <string name="TRL_Currency_name" >Liras</string>
        <string name="TTD_Currency_name" >@string/USD_Currency_name</string>
        <string name="TVD_Currency_name" >@string/USD_Currency_name</string>
        <string name="TWD_Currency_name" >@string/USD_Currency_name</string>
        <string name="TZS_Currency_name" >@string/KES_Currency_name</string>
        <string name="UAH_Currency_name" >Hryvnia</string>
        <string name="UGX_Currency_name" >@string/KES_Currency_name</string>
        <string name="USD_Currency_name" >Dollars</string>
        <string name="UYU_Currency_name" >@string/COP_Currency_name</string>
        <string name="UZS_Currency_name" >Sums</string>
        <string name="XAG_Currency_name" >@string/XAU_Currency_name</string>
        <string name="XAF_Currency_name" >@string/CHF_Currency_name</string>
        <string name="XAU_Currency_name" >Ounces</string>
        <string name="XCD_Currency_name" >@string/USD_Currency_name</string>
        <string name="XDR_Currency_name" >@string/USD_Currency_name</string>
        <string name="XOF_Currency_name" >@string/CHF_Currency_name</string>
        <string name="XPD_Currency_name" >@string/XAU_Currency_name</string>
        <string name="XPF_Currency_name" >@string/CHF_Currency_name</string>
        <string name="XPT_Currency_name" >@string/XAU_Currency_name</string>
        <string name="VEB_Currency_name" >Bolivares</string>
        <string name="VND_Currency_name" >Dong</string>
        <string name="VUV_Currency_name" >Vatu</string>
        <string name="WST_Currency_name" >Tala</string>
        <string name="YER_Currency_name" >@string/IRR_Currency_name</string>
        <string name="YUM_Currency_name" >@string/DZD_Currency_name</string>
        <string name="ZAR_Currency_name" >Rand</string>
        <string name="ZWD_Currency_name" >@string/USD_Currency_name</string>
        <string name="ZMK_Currency_name" >@string/MWK_Currency_name</string>

*/

        Context context = MyApp.getContext();
        abstract void changeCurrency();

    }

}
