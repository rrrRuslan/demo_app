package com.demo.presentation.common

import com.demo.domain.entity.weather.WeatherCondition
import com.demo.domain.entity.weather.WeatherCondition.*
import com.demo.presentation.R

object IconManager {

    fun WeatherCondition.getNotificationIcon(): Int {
        return when {
            else -> when (this) {
                WeatherCondition.ClearNight -> R.drawable.status_clear_night
                Dz, HvRa, RaPossDay, RaPossNight, Rain, Ra -> R.drawable.status_ra
                Fg, FgPoss -> R.drawable.status_fg
                FgPossDay -> R.drawable.status_fg_poss_day
                FgPossNight -> R.drawable.status_fg_poss_night
                Gr, GrPossNight, GrPossDay -> R.drawable.status_gr
                HvSn, Sn, SnPossDay, SnPossNight, LtSn -> R.drawable.status_sn
                Overcast, OvercastAlt1 -> R.drawable.status_overcast
                PtCloudy -> R.drawable.status_pt_cloudy
                PtCloudyNight -> R.drawable.status_pt_cloudy_night
                SunHot13544, Clear -> R.drawable.status_clear
                TsPossDay, TsPossNight, Ts -> R.drawable.status_ts
                Fzra, FzraPossDay, FzraPossNight -> R.drawable.status_fzra
                HvTs -> R.drawable.status_hv_ts
                Hz, SS -> R.drawable.status_hz
                MtCloudyNight -> R.drawable.status_mt_cloudy_night
                MtCloudy -> R.drawable.status_mt_cloudy
                else -> getBwIcon()
            }
        }
    }

    fun WeatherCondition.getColorfulIcon() = when (this) {
        Umbrella -> R.drawable.ic_weather_colorful_umbrella
        Windy -> R.drawable.ic_weather_colorful_windy
        ZrIp -> R.drawable.ic_weather_colorful_zr_ip
        ZrIpPossDay -> R.drawable.ic_weather_colorful_zr_ip_poss_day
        ZrIpPossNight -> R.drawable.ic_weather_colorful_zr_ip_poss_night
        ClearNight -> R.drawable.ic_weather_colorful_clear_night
        Dz -> R.drawable.ic_weather_colorful_dz
        Fg -> R.drawable.ic_weather_colorful_fg
        FgPoss -> R.drawable.ic_weather_colorful_fg
        FgPossDay -> R.drawable.ic_weather_colorful_fg_poss_day
        FgPossNight -> R.drawable.ic_weather_colorful_fg_poss_night
        FzraPossDay -> R.drawable.ic_weather_colorful_fzra_poss_day
        FzraPossNight -> R.drawable.ic_weather_colorful_fzra_poss_night
        Gr -> R.drawable.ic_weather_colorful_gr
        GrPossNight -> R.drawable.ic_weather_colorful_gr_poss_night
        HvRa -> R.drawable.ic_weather_colorful_hv_ra
        HvSn -> R.drawable.ic_weather_colorful_hv_sn
        MtCloudyNight -> R.drawable.ic_weather_colorful_mt_cloudy_night
        Overcast -> R.drawable.ic_weather_colorful_overcast
        PtCloudy -> R.drawable.ic_weather_colorful_mt_cloudy
        OvercastAlt1 -> R.drawable.ic_weather_colorful_overcast_alt1
        PtCloudyNight -> R.drawable.ic_weather_colorful_mt_cloudy_night
        RaPossDay -> R.drawable.ic_weather_colorful_ra_poss_day
        RaPossNight -> R.drawable.ic_weather_colorful_ra_poss_night
        Sn -> R.drawable.ic_weather_colorful_sn
        SnPossDay -> R.drawable.ic_weather_colorful_sn_poss_day
        SnPossNight -> R.drawable.ic_weather_colorful_sn_poss_night
        Snra -> R.drawable.ic_weather_colorful_snra
        SnraPossDay -> R.drawable.ic_weather_colorful_snra_poss_day
        SnraPossNight -> R.drawable.ic_weather_colorful_snra_poss_night
        SunHot13544 -> R.drawable.ic_weather_colorful_sun_hot_13544
        TsPossDay -> R.drawable.ic_weather_colorful_ts_poss_day
        TsPossNight -> R.drawable.ic_weather_colorful_ts_poss_night
        TsRa -> R.drawable.ic_weather_colorful_ts_ra
        Ts -> R.drawable.ic_weather_colorful_ts
        Clear -> R.drawable.ic_weather_colorful_clear
        Fzra -> R.drawable.ic_weather_colorful_fzra
        GrPossDay -> R.drawable.ic_weather_colorful_gr_poss_day
        HvTs -> R.drawable.ic_weather_colorful_hv_ts
        Hz -> R.drawable.ic_weather_colorful_hz
        SS -> R.drawable.ic_weather_colorful_hz
        LtSn -> R.drawable.ic_weather_colorful_lt_sn
        MtCloudy -> R.drawable.ic_weather_colorful_mt_cloudy
        Rain -> R.drawable.ic_weather_colorful_rain
        Ra -> R.drawable.ic_weather_colorful_rain
        Unknown -> android.R.drawable.stat_notify_error
    }

    fun WeatherCondition.getBwIcon() = when (this) {
        Fzra -> R.drawable.ic_weather_bw_fzra
        FzraPossDay -> R.drawable.ic_weather_bw_fzra_poss_day
        FzraPossNight -> R.drawable.ic_weather_bw_fzra_poss_night
        Gr -> R.drawable.ic_weather_bw_gr
        GrPossDay -> R.drawable.ic_weather_bw_gr_poss_day
        GrPossNight -> R.drawable.ic_weather_bw_gr_poss_night
        HvRa -> R.drawable.ic_weather_bw_hv_ra
        HvSn -> R.drawable.ic_weather_bw_hv_sn
        HvTs -> R.drawable.ic_weather_bw_hv_ts
        Hz -> R.drawable.ic_weather_bw_hz
        SS -> R.drawable.ic_weather_bw_hz
        LtSn -> R.drawable.ic_weather_bw_lt_sn
        MtCloudy -> R.drawable.ic_weather_bw_mt_cloudy
        MtCloudyNight -> R.drawable.ic_weather_bw_mt_cloudy_night
        Overcast -> R.drawable.ic_weather_bw_overcast
        PtCloudy -> R.drawable.ic_weather_bw_mt_cloudy
        OvercastAlt1 -> R.drawable.ic_weather_bw_overcast_alt1
        PtCloudyNight -> R.drawable.ic_weather_bw_mt_cloudy_night
        RaPossDay -> R.drawable.ic_weather_bw_ra_poss_day
        RaPossNight -> R.drawable.ic_weather_bw_ra_poss_night
        Rain -> R.drawable.ic_weather_bw_rain
        Ra -> R.drawable.ic_weather_bw_rain
        Sn -> R.drawable.ic_weather_bw_sn
        SnPossDay -> R.drawable.ic_weather_bw_sn_poss_day
        SnPossNight -> R.drawable.ic_weather_bw_sn_poss_night
        Snra -> R.drawable.ic_weather_bw_snra
        SnraPossDay -> R.drawable.ic_weather_bw_snra_poss_day
        SnraPossNight -> R.drawable.ic_weather_bw_snra_poss_night
        SunHot13544 -> R.drawable.ic_weather_bw_sun_hot_13544
        Ts -> R.drawable.ic_weather_bw_ts
        TsPossDay -> R.drawable.ic_weather_bw_ts_poss_day
        TsPossNight -> R.drawable.ic_weather_bw_ts_poss_night
        TsRa -> R.drawable.ic_weather_bw_ts_ra
        Umbrella -> R.drawable.ic_weather_bw_umbrella
        Windy -> R.drawable.ic_weather_bw_windy
        ZrIp -> R.drawable.ic_weather_bw_zr_ip
        ZrIpPossDay -> R.drawable.ic_weather_bw_zr_ip_poss_day
        ZrIpPossNight -> R.drawable.ic_weather_bw_zr_ip_poss_night
        Clear -> R.drawable.ic_weather_bw_clear
        ClearNight -> R.drawable.ic_weather_bw_clear_night
        Dz -> R.drawable.ic_weather_bw_dz
        Fg -> R.drawable.ic_weather_bw_fg
        FgPoss -> R.drawable.ic_weather_colorful_fg
        FgPossDay -> R.drawable.ic_weather_bw_fg_poss_day
        FgPossNight -> R.drawable.ic_weather_bw_fg_poss_night
        Unknown -> android.R.drawable.stat_notify_error
    }

}