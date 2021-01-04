using Newtonsoft.Json;

namespace server.Models
{
    public class Contact : BaseEntity
    {
        [JsonProperty("name")]
        public string Name { get; set; }

        [JsonProperty("number")]
        public string Number { get; set; }
    }
}
