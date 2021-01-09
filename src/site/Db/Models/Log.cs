using Newtonsoft.Json;

namespace site.Db.Models
{
    public class Log : Base
    {
        [JsonProperty("value")]
        public string Value { get; set; }

        [JsonProperty("type")]
        public LogType Type { get; set; } = LogType.none;

        [JsonProperty("deviceId")]
        public string DeviceId { get; set; }
    }
}