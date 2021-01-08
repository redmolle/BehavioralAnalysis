using Newtonsoft.Json;
using bas.Models.Base;

namespace bas.Models
{
    public class Log : IdCreatedModel
    {
        [JsonProperty("value")]
        public string Value { get; set; }

        [JsonProperty("type")]
        public LogType Type { get; set; } = LogType.none;

        [JsonProperty("deviceId")]
        public string DeviceId { get; set; }
    }
}
