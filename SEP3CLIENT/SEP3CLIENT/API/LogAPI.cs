using System;
using System.IO;
using System.Text;

namespace FreshFitness.API
{
    class LogAPI
    {
        private static StreamWriter log;

        public static void init()
        {
            var date = DateTime.Now;
            if (File.Exists("clientLog.log"))
            {
                if (!Directory.Exists("logs/"))
                {
                    Directory.CreateDirectory("logs/");
                }
                File.Move("clientLog.log", $"logs/{date:yyyy-MM-dd}_{DateTime.UtcNow.Subtract(new DateTime(1970, 1, 1)).TotalSeconds}.log");
            }

            log = new StreamWriter(File.Open("clientLog.log", FileMode.OpenOrCreate, FileAccess.ReadWrite, FileShare.Read), Encoding.UTF8);
            log.AutoFlush = true;
        }

        public enum LogLevel
        {
            INFO, DEBUG, WARN, ERROR, FATAL
        }

        public static void AddLog(LogLevel logLevel, String line)
        {
            var date = DateTime.Now;
            log.WriteLine($"[{date:HH:mm:ss}] [{logLevel}] {line}");
        }

    }
}
