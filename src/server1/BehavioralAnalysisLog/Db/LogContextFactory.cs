using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;
using Microsoft.Extensions.Configuration;
using System.IO;

namespace server.Db
{
    public class LogContextFactory : IDesignTimeDbContextFactory<LogContext>
    {
        public LogContext CreateDbContext(string[] args)
        {
            IConfigurationRoot configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json")
                .Build();

            var optionsBuilder = new DbContextOptionsBuilder<LogContext>();
            optionsBuilder.UseNpgsql(configuration.GetConnectionString("LogContext"));

            return new LogContext(optionsBuilder.Options);
        }
    }
}
