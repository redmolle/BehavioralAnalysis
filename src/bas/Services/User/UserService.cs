using bas;
using bas.Db.Repository.User;
using bas.Models;

namespace bas.Services.User
{
    public class UserService : IUserService
    {
        public UserService(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        private readonly IUserRepository _userRepository;

        public string GetUserRole(string userName)
        {
            var user = _userRepository.GetUserOrNull(userName);

            if (user == null)
            {
                return string.Empty;
            }

            return user.IsAdmin ? UserRole.Admin : UserRole.Basic;
        }

        public bool IsAnExistingUser(string userName)
        { 
            return _userRepository.GetUserOrNull(userName) != null;
        }

        public bool IsValidUserCredentials(string userName, string password)
        {
            var user = _userRepository.GetUserOrNull(userName);

            if (user == null)
            {
                return false;
            }

            return user.Password == password;
        }
    }
}
