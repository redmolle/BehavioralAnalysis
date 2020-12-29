using Newtonsoft.Json;

namespace BehavioralAnalysisLog.Models
{
    public class LogBase : BaseEntity
    {
        [JsonProperty("type")]
        public LogType Type { get; set; }
    }
}
