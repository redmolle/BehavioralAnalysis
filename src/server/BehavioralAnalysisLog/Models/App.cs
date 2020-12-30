using Newtonsoft.Json;

namespace BehavioralAnalysisLog.Models
{
    public class App : BaseEntity
    {
        [JsonProperty("appName")]
        public string AppName { get; set; }

        [JsonProperty("packageName")]
        public string PackageName { get; set; }

        [JsonProperty("varsionName")]
        public string VersionName { get; set; }

        [JsonProperty("versionCode")]
        public string VersionCode { get; set; }
    }
}
