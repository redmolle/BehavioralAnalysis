using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace bas.Services.User.Interfaces
{
    public interface IUserService
    {
        bool IsAnExistingUser(string userName);
        bool IsValidUserCredentials(string userName, string password);
        string GetUserRole(string userName);
    }
}
