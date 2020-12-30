using Newtonsoft.Json;

namespace BehavioralAnalysisLog.Models
{
    public class RawLog : LogBase
    {
        [JsonProperty("value")]
        public string Value { get; set; }
    }
}
