package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


   @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        HashMap<String,Object> head = new HashMap<String, Object>();
        String keywords = "";
        Integer count = 0;
        Scanner s = new Scanner(q);
        s.useDelimiter(" ");
        while (s.hasNext()){
            String aux = s.next();
            if(aux.contains("max:")){
                String []aux2 = aux.split(":");
                count = new Integer(aux2[1]);
            }else{
                keywords = keywords + " " + aux;
            }
        }
        head.put("CamelTwitterKeywords",keywords);
        head.put("CamelTwitterCount",count);

        return producerTemplate.requestBodyAndHeaders("direct:search","",head);
    }
}
