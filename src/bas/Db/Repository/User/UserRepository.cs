using bas.Db.Factory;
using System.Linq;

namespace bas.Db.Repository.User
{
    public class UserRepository : Repository, IUserRepository
    {
        public UserRepository(IContextFactory contextFactory)
            : base(contextFactory)
        {
        }

        public Models.User GetUserOrNull(string name)
        {
            if (string.IsNullOrEmpty(name))
            {
                return null;
            }

            using (var context = _contextFactory.CreateDbContext())
            {
                return context.User.FirstOrDefault(x => x.Name == name);
            }
        }
    }
}
