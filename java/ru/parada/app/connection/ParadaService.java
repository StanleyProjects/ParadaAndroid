package ru.parada.app.connection;

public interface ParadaService
{
    String BASE_URL = "http://www.mobile.baltclinicvo.ru/parada/backend/";
    interface Get
    {
        String NEWS = "get_news.php";
        String ACTIONS = "get_actions.php";
        String SERVICES = "get_services.php";
        String DOCTORS = "get_doctors.php";
        String SERVICES_WITH_PRICES = "get_services_with_prices.php";
        String PRICES = "get_prices.php";
    }
}