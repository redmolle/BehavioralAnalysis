using Microsoft.EntityFrameworkCore;
using site.Db.Configurations;
using site.Db.Models;

namespace site.Db
{
    public class Context : DbContext
    {
        public const string CONNECTION_SECTION_NAME = "DbContext";

        public Context(DbContextOptions<Context> options)
            : base(options)
        {
        }

        public DbSet<Log> Log { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            new LogEntityTypeConfiguration().Configure(modelBuilder.Entity<Log>());
        }
    }
}