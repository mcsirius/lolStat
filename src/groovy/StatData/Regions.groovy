package StatData

/**
 * Created by zhiyu on 11/18/15.
 */
enum Regions {
    NA("na"),
    BR("br"),
    EUNE("eune"),
    EUW("euw"),
    KR("kr"),
    LAN("lan"),
    LAS("las"),
    OCE("oce"),
    PBE("pbe"),
    RU("ru"),
    TR("tr")

    String region

    public Regions (String region){
        this.region = region
    }
}