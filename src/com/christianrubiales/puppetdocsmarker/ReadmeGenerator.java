package com.christianrubiales.puppetdocsmarker;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ReadmeGenerator {

	static String HEADER = "# Puppet-Docs-Marker-Template\n"
			+ "Mark what sections you have read from the Puppet Docs\n\n";
	static String BASE = "https://puppet.com";
	static String VERSION = "6.2";
	static String LATEST = "latest";

	public static void main(String[] args) throws Exception {
		System.out.println(HEADER);
		File file = new File("./index.html");
		Document doc = Jsoup.parse(file, "UTF-8");
		Element main = doc.selectFirst(".documentation-navigation");
		System.out.println("# " + main.selectFirst("h3").text() + "\n");
		main = doc.selectFirst("ul");
		
		for (Element heading : main.children()) {
			Element tag = heading.selectFirst("a");
			System.out.println("## [" + tag.text() + "](" + processLink(tag.attr("href")) + ")");
			
			Element first = heading.selectFirst("ul");
			for (Element li : first.children()) {
				Element second = li.selectFirst("ul");
				if (second == null) {
					Element firstTag = li.selectFirst("a");
					System.out.println("- [ ] [" + firstTag.text() + "]"
							+ "(" + processLink(firstTag.attr("href")) + ")");
				} else {
					System.out.println();
					Element firstTag = li.selectFirst("a");
					if (firstTag != null) {
						System.out.println("### [" + firstTag.text() + "]"
								+ "(" + processLink(firstTag.attr("href")) + ")");
					} else {
						System.out.println("### " + li.selectFirst("strong").text());
					}

					for (Element secondLi : second.getElementsByTag("li")) {
						Element secondTag = secondLi.selectFirst("a");
						System.out.println("- [ ] [" + secondTag.text() + "]"
								+ "(" + processLink(secondTag.attr("href")) + ")");
					}
					System.out.println("## ");
				}
			}
			System.out.println();
		}
	}
	
	static String processLink(String href) {
		return BASE + href.replace(VERSION, LATEST);
	}

}
