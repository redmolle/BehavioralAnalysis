using Newtonsoft.Json;

namespace bas.Controllers.Account
{
    public class LoginResult
    {
        [JsonProperty("username")]
        public string UserName { get; set; }

        [JsonProperty("role")]
        public string Role { get; set; }

        [JsonProperty("originalUserName")]
        public string OriginalUserName { get; set; }

        [JsonProperty("accessToken")]
        public string AccessToken { get; set; }

        [JsonProperty("refreshToken")]
        public string RefreshToken { get; set; }
    }
}
