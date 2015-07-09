package it.sevenbits.FacultySite.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    private static Logger LOG = Logger.getLogger(HomeController.class);

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/main";
    }

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="NewsType", required = false) String newsType, @RequestParam(value="NewsId", required = false) String newsId, Model model) {
        LOG.info("News type param: " + newsType);
        LOG.info("News id param: " + newsId);
        if (newsType == null)
            newsType = "All-news";
        model.addAttribute("newsType", newsType);
        try {
            if (newsId == null || Integer.parseInt(newsId) < 1)
                newsId = "0";
        }
        catch (Exception e){
            LOG.info("NewsId is incorrect: " + newsId);
            newsId = "0";
        }
        String  oldText = "Тут должно быть много текста, давайте почитаем детские рассуждения:) \nБЕССОНИЦА Это может быть у невесты. Лежит она ночью и думает:«Какое у меня завтраплатье будет? Красивое или нет? А главное – какой у меня завтра муж будет?» (Маша, 7 лет) \nБОРТПРОВОДНИЦА Она должна быть обязательно худой. А то, если она будет толстая, оназастрянет между рядами. Придется пилоту выходить и проталкивать ее попроходу. А это не очень удобно. (Даша, 7 лет)    \nВЛЮБЛЁННЫЙ Вот, например, муж за женой ходит-ходит, глаз с нее не сводит целый день. Итогда она начинает догадываться, что он в нее, видимо, влюблен. (Марина, 8 лет)    \nЖЕНА Это девушка, которая готовит мужчине обед, стирает белье и ухаживает за егоребенком. (Андрей, 4 года)    \nМУЖ С этим человеком сложно. Потому что с ним много хлопот… Расходыбольшие… Подвести тебя этот человек может: например, сначала он былкрасивый и хороший, а после того, как ты на нем женился, стал ругучим итолстым. (Кирилл, 7 лет)    \nЖЕНИХ Так дяденьку называют до того, как он женится. А после того, как он женится,его уже называют другими разными словами… (Аня, 9 лет)    \nИНТУИЦИЯ У кого она есть, тот к двери подходит и уже сразу чувствует, что за ней егоподжидают. И поэтому заранее достает пистолет, врывается и без лишних словначинает стрелять. (Федя, 8 лет)    \nКРУИЗ После него мужчинам и женщинам часто приходится жениться. (Лена, 7 лет)    \nМАНЕКЕНЩИЦА Профессия тяжелая, потому что нужно все время сидеть на диете и быстроснимать с себя одежду. (Ира Мазунова, 9 лет)    \nПОЭТ Приходит к нему муза, а потом опять уходит. И он наполовину рад, анаполовину расстроен. Рад – потому что она приходила, а расстроен, потомучто теперь целый год ждать, когда снова придет (Женя Новиков, 9 лет)    \nПСЕВДОНИМ Это артисты придумывают себе какое-нибудь красивое имя, чтобы в программкахписать. А у самих – некрасивое. Бывает и у писателей: они сочиняюткакие-нибудь стихи, а имя напишут другого писателя. (Женя Пак, 7 лет)    \nСТИРАЛЬНЫЙ ПОРОШОК Обычно это насыпают в стиральную машину. А вот что будет, если его насыпатьв суп, я не знаю. Потому что, еще не пробовала… (Даша Ушакова, 8 лет)    \nСЧАСТЬЕ У детей этого половина на половину. Потому что мама то ругает, то мороженоепокупает. (Зульфия Хакимова, 8 лет)    \nТЕЛЕСЕРИАЛ Это больше всего нравится женщинам, потому что там всякие захватывающиесобытия происходят. Мужчин, например, убивают по нескольку штук сразу. Оченьзахватывающе! (Арина, 7 лет)    \nХВОСТ Он приделан к зверям сзади. Например, корова кончается, и начинается он. (Оля Лучкова, 4 года)    \nШЕРСТЬ У рыб этого не может быть. Потому что если они ею покроются, то им будеточень жарко под водой плавать. (Юля Лебедева, 8 лет)    \nКесарево сечение значит когда ребенка вырезают через запасной выход. Мартина6 лет Мой папа говорит что моя мама без сознания когда она покупает одежду. Марион 6 лет.";
        model.addAttribute("newsId", newsId);
        model.addAttribute("oldText", oldText);
        return "home/news";
    }

    @RequestMapping(value = "/main")
    public String main() {
        return "home/main";
    }

    @RequestMapping(value = "/applicants")
    public String applicants() {
        return "home/applicants";
    }

    @RequestMapping(value = "/gallery")
    public String gallery() {
        return "home/gallery";
    }

    @RequestMapping(value = "/contacts")
    public String contacts() {
        return "home/contacts";
    }
}
