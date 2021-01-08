using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;
using Microsoft.Extensions.Configuration;
using System.IO;

namespace bas.Db
{
    public class ContextFactory : IDesignTimeDbContextFactory<Context>
    {
        public Context CreateDbContext()
        {
            var optionsBuilder = new DbContextOptionsBuilder<Context>();
            SetConnection(optionsBuilder);
            return new Context(optionsBuilder.Options);
        }

        public Context CreateDbContext(string[] args)
        {
            return CreateDbContext();
        }

        private static void SetConnection(DbContextOptionsBuilder options)
        {
            IConfigurationRoot configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json")
                .Build();

            options.UseSqlServer(configuration.GetConnectionString(Context.CONNECTION_SECTION_NAME));
        }
    }
}
