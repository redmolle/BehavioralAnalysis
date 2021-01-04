namespace server.Extensions
{
    public static class StringExtensions
    {
        public static bool EqualsIgnoreCase(this string value, string toCompare)
        {
            var first = (string.IsNullOrWhiteSpace(value) ? string.Empty : value).ToLower();
            var second = (string.IsNullOrWhiteSpace(toCompare) ? string.Empty : toCompare).ToLower();
            return first == second;
        }
    }
}
