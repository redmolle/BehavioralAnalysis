using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

namespace bas.Controllers.Account
{
    public class LoginRequest
    {
        [Required]
        [JsonProperty("username")]
        public string UserName { get; set; }

        [Required]
        [JsonProperty("password")]
        public string Password { get; set; }
    }
}
