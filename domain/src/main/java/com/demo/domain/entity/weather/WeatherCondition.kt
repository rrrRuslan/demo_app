package com.demo.domain.entity.weather

enum class WeatherCondition(val condition: String) {

    Umbrella("umbrella"),
    Windy("windy"),
    ZrIp("zr_ip"),
    ZrIpPossDay("zr_ip_poss_day"),
    ZrIpPossNight("zr_ip_poss_night"),
    ClearNight("clear_night"),
    Dz("dz"),
    Fg("fg"),
    FgPoss("fg_poss"),
    FgPossDay("fg_poss_day"),
    FgPossNight("fg_poss_night"),
    Gr("gr"),
    GrPossNight("gr_poss_night"),
    HvRa("hv_ra"),
    HvSn("hv_sn"),
    MtCloudyNight("mt_cloudy_night"),
    Overcast("overcast"),
    PtCloudy("pt_cloudy"),
    OvercastAlt1("overcast_alt1"),
    PtCloudyNight("pt_cloudy_night"),
    RaPossDay("ra_poss_day"),
    SS("ss"),
    RaPossNight("ra_poss_night"),
    Sn("sn"),
    SnPossDay("sn_poss_day"),
    SnPossNight("sn_poss_night"),
    Snra("snra"),
    SnraPossDay("snra_poss_day"),
    SnraPossNight("snra_poss_night"),
    SunHot13544("sun_hot_13544"),
    TsPossDay("ts_poss_day"),
    TsPossNight("ts_poss_night"),
    TsRa("ts_ra"),
    Ts("ts"),
    Clear("clear"),
    Fzra("fzra"),
    FzraPossDay("fzra_poss_day"),
    FzraPossNight("fzra_poss_night"),
    GrPossDay("gr_poss_day"),
    HvTs("hv_ts"),
    Hz("hz"),
    LtSn("lt_sn"),
    MtCloudy("mt_cloudy"),
    Rain("rain"),
    Ra("ra"),
    Unknown("UNKNOWN");

    companion object {
        fun fromString(condition: String?): WeatherCondition {
            condition ?: return Unknown
            return entries.firstOrNull { it.condition == condition } ?: Unknown
        }
    }

    val isRain: Boolean
        get() = when (this) {
            Ra, Rain, TsRa, HvRa, HvTs, RaPossDay,
            RaPossNight, TsPossDay, TsPossNight,
            Ts, ZrIpPossDay, ZrIpPossNight, ZrIp, Dz
            -> true

            else -> false
        }

    val isFreezingRain: Boolean
        get() = when (this) {
            FzraPossNight, FzraPossDay, Fzra -> true
            else -> false
        }

    val isSnow: Boolean
        get() = when (this) {
            SnPossDay, HvSn, SnPossNight, Sn, SnraPossDay, SnraPossNight, Snra -> true
            else -> false
        }

    val isOvercast: Boolean
        get() = when (this) {
            FgPossDay, FgPossNight, Fg, Overcast, OvercastAlt1, Hz -> true
            else -> false
        }

    val isMostlyCloudy: Boolean
        get() = when (this) {
            MtCloudy, MtCloudyNight -> true
            else -> false
        }

    val isPartlyCloudy: Boolean
        get() = when (this) {
            PtCloudy, PtCloudyNight -> true
            else -> false
        }

}