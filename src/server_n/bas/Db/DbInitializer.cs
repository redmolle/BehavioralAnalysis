using bas.Extensions;
using System.Linq;

namespace bas.Db
{
    public class DbInitializer
    {
        private const string ADMIN = "admin";
        public static void Initialize(Context context)
        {
            context.Database.EnsureCreated();

            var user = context.User.FirstOrDefault(x => string.Equals(x.Name, ADMIN, System.StringComparison.CurrentCultureIgnoreCase));

            if (user == null)
            {
                user = new Models.User
                {
                    Name = ADMIN,
                    Password = "password",
                    IsAdmin = true
                };
                context.User.Add(user);
                context.SaveChanges();
            }
        }
    }
}
