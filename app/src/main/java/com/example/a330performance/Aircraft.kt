

data class Aircraft(
    val type: String,
    val info: Info
)

data class Info(
    val manufacturer: String,
    val model: String,
    val engines: List<Engine>,
    val dimensions: Dimensions,
    val weight: Weight,
    val fuelCapacity: FuelCapacity,
    val flapConfigurations: List<FlapConfiguration>,
    val features: Features,
    val temperatureFactor: Int,
    val trim: Trim,
    val speeds: Speeds
)

data class Engine(val type: String, val model: String, val thrust: String)
data class Dimensions(val length: String, val wingspan: String, val height: String, val cabinWidth: String, val wingArea: String)
data class Weight(val empty: String, val maxTakeoff: String, val maxLanding: String, val maxMZFW: String)
data class FuelCapacity(val mainTanks: String, val centerTank: String)
data class FlapConfiguration(val type: String, val options: List<String>)
data class Features(val antiIce: List<String>, val airConditioning: Boolean)
data class Trim(val interpolation: Int, val maxCG: Int, val minCG: Int, val max: Double, val min: Double)
data class Speeds(val flapsRetr: Map<String, Int>, val slatsRetr: Map<String, Int>, val VSpeeds: VSpeeds)
data class VSpeeds(val v1factor: Int,
                   val vrfactor: Int,
                   val `1`: Map<String, Int>,
                   val `2`: Map<String, Int>,
                   val `3`: Map<String, Int>)

