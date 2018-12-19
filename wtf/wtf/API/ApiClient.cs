using Client.API;
using RestSharp;

namespace FreshFitness.API
{
    class ApiClient
    {
        private RestClient client;

        public ApiClient ()
        {
            client = new RestClient(Endpoint.getServerUrl());
            client.RemoteCertificateValidationCallback += (sender,certificate,chain,sslPolicyErrors) => true; // Ignore SSL/TLS errors
            client.UserAgent = "Jas-o-client LOL";
            client.CookieContainer = new System.Net.CookieContainer(); // We need it because server uses it for authenticationB
        }

        public RestClient getClient()
        {
            return client;
        }

        public bool isAPIAvailable()
        {
            var request = new RestRequest(Endpoint.STATUS.ToString(), Method.GET);
            var response = client.Execute(request);
            
            if (response.ErrorException != null)
            {
                return false;
            }

            return response.IsSuccessful;
        }

        public IRestResponse logIn(string mail, string pass) 
        {
            var request = new RestRequest(Endpoint.LOGIN.ToString(), Method.POST);
            request.AddQueryParameter("email", mail);
            request.AddQueryParameter("password", pass);

            return client.Execute(request);
        }
    }
}
