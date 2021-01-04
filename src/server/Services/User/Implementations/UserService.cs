using Microsoft.Extensions.Logging;
using server.Db;
using server.Extensions;
using server.Services.User.Interfaces;
using System.Linq;

namespace server.Services.User.Implementations
{
    public class UserService : IUserService
    {
        public UserService(ILogger<UserService> logger, Context context)
        {
            _logger = logger;
            _context = context;
        }

        private readonly ILogger<UserService> _logger;
        private readonly Context _context;

        public string GetUserRole(string userName)
        {
            var user = _context.User.FirstOrDefault(x => x.Name == userName);

            if (user == null)
            {
                return string.Empty;
            }

            return user.IsAdmin
                ? UserRole.Admin
                : UserRole.Basic;
        }

        public bool IsAnExistingUser(string userName)
        {
            return _context.User.Any(x => x.Name == userName);
        }

        public bool IsValidUserCredentials(string userName, string password)
        {
            _logger.LogInformation($"Validating user [{userName}]");

            if (string.IsNullOrWhiteSpace(userName) || string.IsNullOrWhiteSpace(password))
            {
                return false;
            }
            var user = _context.User.FirstOrDefault(x => x.Name == userName);

            return user == null
                ? false
                : user.Password == password;
        }
    }
}
