package com.tune.englishblog.util;

/**
 * Created by TungNT on 19/02/2016.
 */
public interface Constants {
    public static final String MIME_TYPE = "text/html";
    public static final String ENCODING = "UTF-8";

    public static final String HTML_HEAD = "<html><head> <style type='text/css'> "
            + "body {max-width: 100%; margin: 0.3cm; font-family: sans-serif-light; color: #000000; background-color: #f6f6f6; line-height: 150%} "
            + "* {max-width: 100%; word-break: break-word}"
            + "h1, h2 {font-weight: normal; line-height: 130%} "
            + "h1 {font-size: 170%; margin-bottom: 0.1em} "
            + "h2 {font-size: 140%} "
            + "a {color: #0099CC}"
            + "h1 a {color: inherit; text-decoration: none}"
            + "img {height: auto} "
            + "pre {white-space: pre-wrap;} "
            + ".blockquote {border-left: thick solid #a6a6a6; background-color: #e6e6e6; margin: 0.5em 0 0.5em 0em; padding: 0.5em} "
            + "p {margin: 0.8em 0 0.8em 0} "
            + "p.subtitle {color: #666666; border-top:1px solid #ddd; border-bottom:1px solid #ddd; padding-top:2px; padding-bottom:2px; font-weight:800 } "
            + "ul, ol {margin: 0 0 0.8em 0.6em; padding: 0 0 0 1em} "
            + "ul li, ol li {margin: 0 0 0.8em 0; padding: 0} "
            + ".item_answare_ama a{color: #333!important;text-decoration: none;}"
            + "</style><meta name='viewport' content='width=device-width'/>"
            + "<link href='http://st.f3.vnecdn.net/responsive/c/v54/giaoduc.css' media='all' rel='stylesheet' type='text/css' />"
//            + "<link rel='stylesheet' href='http://st.f3.vnecdn.net/responsive/c/v54/general.css' media='all' />"
            + "</head><body>";
    public static final String HTML_BODY_END =
            "</body><script language='javascript' type='text/javascript'> var interactions_url = 'http://usi.saas.vnexpress.net';" +
               " var base_url = 'http://vnexpress.net'; var css_url = 'http://st.f3.vnecdn.net/responsive/c/v54';" +
               " var js_url = 'http://st.f2.vnecdn.net/responsive/j/v5'; var flash_url = 'http://st.f4.vnecdn.net/responsive/f/v19';" +
               " var img_url = 'http://st.f1.vnecdn.net/responsive/i/v24'; var image_cloud = 'http://l.f29.img.vnecdn.net';" +
               " var PageHot = 0; var device_env = 4; var site_id = 1000000; var SITE_ID =  1000000; var PAGE_FOLDER =  1003698; var PAGE_DETAIL = 1;" +
            "</script>"
            + "<script type='text/javascript' src='http://st.f2.vnecdn.net/responsive/libs/jquery-1.7.1.min.js'></script>"
            + "<script type='text/javascript' src='http://st.f2.vnecdn.net/responsive/j/v5/interactions/quiz.widget.js'></script>"
            + "</html>";
    public static final String TITLE_START = "<h1>";
    public static final String TITLE_END = "</h1>";
}
