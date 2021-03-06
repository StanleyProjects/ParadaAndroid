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
        String INFO = "get_info.php";
        String NOTIFICATIONS = "get_push.php";
        String VIDEOS = "get_videos.php";
        String PUSH_TOKEN = "save_push_token.php";
    }
    interface Post
    {
        String SUBSCRIBE_DATA = "goto_action.php";
        String REQUESTCALL_DATA = "call_action.php";
    }
}