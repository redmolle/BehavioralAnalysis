using bas.Db;
using bas.Models;
using System.Linq;

namespace bas.Services.User
{
    public class UserService : IUserService
    {
        public UserService(Context context)
        {
            _context = context;
        }

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
