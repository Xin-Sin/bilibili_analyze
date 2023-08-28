package top.xinsin.parser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import top.xinsin.util.GetHttpClient;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static top.xinsin.util.StringConstant.*;

/**
 * @author xinsin
 * Created On 2023/8/28 10:11
 * @version 1.0
 */
public class VideoParser {

    public String parse(String bv){
        OkHttpClient instance = GetHttpClient.getInstance();
        String url = String.format(BILIBILI_URL, bv);
        Request request = new Request.Builder().url(url).build();

        try(Response response = instance.newCall(request).execute()){
            return parseContent(response.body().string(), url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String parseContent(String content,String url){
        StringBuilder stringBuilder = new StringBuilder();

        Document parse = Jsoup.parse(content);
        Pattern coverPattern = Pattern.compile(COVER_REGEX);
        Matcher coverMatcher = coverPattern.matcher(parse.html());
        String coverUrl = null;
        if (coverMatcher.find()){
            coverUrl = HTTPS_PRE_FIX + coverMatcher.group(1).replace("//","");

        }
        Elements titleElements = parse.selectXpath("/html/body/div[2]/div[2]/div[1]/div[1]/h1");
        String title = titleElements.get(0).html();
        Elements descriptionElements = parse.selectXpath("/html/body/div[2]/div[2]/div[1]/div[4]/div[1]/div/span");
        String description = descriptionElements.get(0).html();
        Elements authorElements = parse.selectXpath("/html/body/div[2]/div[2]/div[2]/div/div[1]/div[1]/div[2]/div[1]/div/div[1]/a[1]");
        String author = authorElements.get(0).html().replace("<span class=\"mask\" data-v-079d0296></span>","");
        String author_url = HTTPS_PRE_FIX + authorElements.get(0).attr("href").replace("//","");
        Elements releaseTimeElements = parse.selectXpath("/html/body/div[2]/div[2]/div[1]/div[1]/div/div/span[3]/span/span");
        String releaseTime = releaseTimeElements.get(0).html();
        Elements likeElements = parse.selectXpath("/html/body/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div/span");
        String likeNum = likeElements.get(0).html();
        Elements coinElements = parse.selectXpath("/html/body/div[2]/div[2]/div[1]/div[3]/div[1]/div[2]/div/span");
        String coinNum = coinElements.get(0).html();
        Elements starElements = parse.selectXpath("/html/body/div[2]/div[2]/div[1]/div[3]/div[1]/div[3]/div/span");
        String starNum = starElements.get(0).html();

        stringBuilder.append("标题: ")
                .append(title)
                .append("\n作者: ")
                .append(author)
                .append("(")
                .append(author_url)
                .append(")\n地址: ")
                .append(url)
                .append("\n")
                .append(coverUrl)
                .append("\n发布时间: ")
                .append(releaseTime)
                .append(" 简介: ")
                .append(description)
                .append("\n点赞: ")
                .append(likeNum)
                .append(" 投币: ")
                .append(coinNum)
                .append(" 收藏: ")
                .append(starNum);

        return stringBuilder.toString();
    }

}
