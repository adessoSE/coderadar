package io.reflectoring.coderadar.dependencyMap.domain;

import com.google.re2j.Pattern;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegexPatternCache {

    private Map<RegexPattern, Pattern> patternMap;

    public RegexPatternCache(){
        patternMap = new HashMap<>();
    }

    public Pattern getPattern(String regex,int flags){
        RegexPattern regexPattern = new RegexPattern(regex,flags);
        return getOrCreate(regexPattern);
    }

    public Pattern getPattern(String regex){
        return getPattern(regex,0);
    }

    public boolean matches(String regex,String string){
        RegexPattern regexPattern = new RegexPattern(regex,0);
        Pattern pattern = getOrCreate(regexPattern);
        return pattern.matcher(string).matches();
    }


    private Pattern getOrCreate(RegexPattern regexPattern){
        if(patternMap.containsKey(regexPattern)){
            return patternMap.get(regexPattern);
        }
        Pattern newPattern = Pattern.compile(regexPattern.regex,regexPattern.flags);
        patternMap.put(regexPattern,newPattern);
        return newPattern;
    }

    class RegexPattern{

        String regex;
        int flags;

        public RegexPattern(String regex,int flags){
            this.regex = regex;
            this.flags = flags;
        }

        public boolean equals(Object obj){
            RegexPattern other = (RegexPattern)obj;
            if(other.regex.equals(regex)&&other.flags==flags)return true;
            return false;
        }

        public int hashCode(){
            return Objects.hash(regex,flags);
        }

    }
}