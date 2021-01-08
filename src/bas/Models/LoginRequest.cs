using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

namespace bas.Models
{
    public class LoginRequest
    {
        [JsonProperty("username")]
        public string UserName { get; set; }

        [JsonProperty("password")]
        public string Password { get; set; }
    }
}
