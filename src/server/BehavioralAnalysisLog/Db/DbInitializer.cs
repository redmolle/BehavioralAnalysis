namespace BehavioralAnalysisLog.Db
{
    public class DbInitializer
    {
        public static void Initialize(LogContext context)
        {
            context.Database.EnsureCreated();
        }
    }
}
