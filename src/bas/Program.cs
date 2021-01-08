using bas.Db;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Server.Kestrel.Core;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using System;
using System.Security.Authentication;

namespace bas
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var host = CreateHostBuilder(args).Build();
            CreateDbIfNotExists(host);
            host.Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder
                    //.ConfigureKestrel(serverOptions =>
                    //{
                    //    serverOptions.Limits.MinRequestBodyDataRate = new MinDataRate(100, TimeSpan.FromSeconds(10));
                    //    serverOptions.Limits.MinResponseDataRate = new MinDataRate(100, TimeSpan.FromSeconds(10));
                    //    serverOptions.Limits.KeepAliveTimeout = TimeSpan.FromMinutes(2);
                    //    serverOptions.Limits.RequestHeadersTimeout = TimeSpan.FromMinutes(1);
                    //    serverOptions.ConfigureHttpsDefaults(listenOptions =>
                    //    {
                    //        listenOptions.SslProtocols = SslProtocols.Tls12;
                    //    });
                    //})
                    .UseStartup<Startup>();
                });

        private static void CreateDbIfNotExists(IHost host)
        {
            using (var scope = host.Services.CreateScope())
            {
                var services = scope.ServiceProvider;
                //try
                //{
                //    var context = services.GetRequiredService<Context>();
                //    DbInitializer.Initialize(context);
                //}
                //catch (Exception ex)
                //{
                //    var logger = services.GetRequiredService<ILogger<Program>>();
                //    logger.LogError(ex, "On creating Db");
                //}
            }
        }
    }
}
