using BehavioralAnalysisLog.Models;
using Microsoft.EntityFrameworkCore;

namespace BehavioralAnalysisLog.Db
{
    public class LogContext : DbContext
    {
        public LogContext(DbContextOptions<LogContext> options)
            : base(options)
        {
        }

        public DbSet<Raw> Raw { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            new RawEntityTypeConfiguration().Configure(modelBuilder.Entity<Raw>());
        }
    }
}
