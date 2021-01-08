using bas.Models;

namespace bas.Db
{
    public class DbInitializer
    {
        public static void Initialize(Context context)
        {
            context.Database.EnsureCreated();

            var user = new User
            {
                Name = "admin",
                Password = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8",
                IsAdmin = true
            };
            context.User.Add(user);
            context.SaveChanges();
        }
    }
}
