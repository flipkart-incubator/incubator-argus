/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

 package com.xasecure.authorization.utils;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class StringUtil {

	public static boolean equals(String str1, String str2) {
		boolean ret = false;

		if(str1 == null) {
			ret = str2 == null;
		} else if(str2 == null) {
			ret = false;
		} else {
			ret = str1.equals(str2);
		}

		return ret;
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		boolean ret = false;

		if(str1 == null) {
			ret = str2 == null;
		} else if(str2 == null) {
			ret = false;
		} else {
			ret = str1.equalsIgnoreCase(str2);
		}

		return ret;
	}

	public static boolean equals(Collection<String> set1, Collection<String> set2) {
		boolean ret = false;

		if(set1 == null) {
			ret = set2 == null;
		} else if(set2 == null) {
			ret = false;
		} else if(set1.size() == set2.size()) {
			ret = set1.containsAll(set2);
		}

		return ret;
	}

	public static boolean equalsIgnoreCase(Collection<String> set1, Collection<String> set2) {
		boolean ret = false;

		if(set1 == null) {
			ret = set2 == null;
		} else if(set2 == null) {
			ret = false;
		} else if(set1.size() == set2.size()) {
			int numFound = 0;

			for(String str1 : set1) {
				boolean str1Found = false;

				for(String str2 : set2) {
					if(equalsIgnoreCase(str1, str2)) {
						str1Found = true;

						break;
					}
				}
				
				if(str1Found) {
					numFound++;
				} else {
					break;
				}
			}

			ret = numFound == set1.size();
		}

		return ret;
	}

	public static boolean matches(String pattern, String str) {
		boolean ret = false;

		if(pattern == null || str == null || pattern.isEmpty() || str.isEmpty()) {
			ret = true;
		} else {
			ret = str.matches(pattern);
		}

		return ret;
	}

	/*
	public static boolean matches(Collection<String> patternSet, Collection<String> strSet) {
		boolean ret = false;

		if(patternSet == null || strSet == null || patternSet.isEmpty() || strSet.isEmpty()) {
			ret = true;
		} else {
			boolean foundUnmatched = false;

			for(String str : strSet) {
				boolean isMatched = false;
				for(String pattern : patternSet) {
					isMatched = str.matches(pattern);
					
					if(isMatched) {
						break;
					}
				}
				
				foundUnmatched = ! isMatched;
				
				if(foundUnmatched) {
					break;
				}
			}
			
			ret = !foundUnmatched;
		}

		return ret;
	}
	*/

	public static boolean contains(String str, String strToFind) {
		boolean ret = false;

		if(str != null && strToFind != null) {
			ret = str.contains(strToFind);
		}

		return ret;
	}

	public static boolean containsIgnoreCase(String str, String strToFind) {
		boolean ret = false;

		if(str != null && strToFind != null) {
			ret = str.toLowerCase().contains(strToFind.toLowerCase());
		}

		return ret;
	}

	public static boolean contains(String[] strArr, String str) {
		boolean ret = false;

		if(strArr != null && strArr.length > 0 && str != null) {
			for(String s : strArr) {
				ret = equals(s, str);

				if(ret) {
					break;
				}
			}
		}

		return ret;
	}

	public static boolean containsIgnoreCase(String[] strArr, String str) {
		boolean ret = false;

		if(strArr != null && strArr.length > 0 && str != null) {
			for(String s : strArr) {
				ret = equalsIgnoreCase(s, str);
				
				if(ret) {
					break;
				}
			}
		}

		return ret;
	}

	public static String toString(Iterable<String> iterable) {
		String ret = "";

		if(iterable != null) {
			int count = 0;
			for(String str : iterable) {
				if(count == 0)
					ret = str;
				else
					ret += (", " + str);
				count++;
			}
		}
		
		return ret;
	}

	public static String toString(String[] arr) {
		String ret = "";

		if(arr != null && arr.length > 0) {
			ret = arr[0];
			for(int i = 1; i < arr.length; i++) {
				ret += (", " + arr[i]);
			}
		}
		
		return ret;
	}

	public static String toString(List<String> arr) {
		String ret = "";

		if(arr != null && arr.size() > 0) {
			ret = arr.get(0);
			for(int i = 1; i < arr.size(); i++) {
				ret += (", " + arr.get(i));
			}
		}
		
		return ret;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static <T> boolean isEmpty(Collection<T> set) {
		return set == null || set.isEmpty();
	}
	
	public static String toLower(String str) {
		return str == null ? null : str.toLowerCase();
	}

	public static byte[] getBytes(String str) {
		return str == null ? null : str.getBytes();
	}

	public static Date getUTCDate() {
	    Calendar local  = Calendar.getInstance();
	    int      offset = local.getTimeZone().getOffset(local.getTimeInMillis());

	    GregorianCalendar utc = new GregorianCalendar(TimeZone.getTimeZone("GMT+0"));

	    utc.setTimeInMillis(local.getTimeInMillis());
	    utc.add(Calendar.MILLISECOND, -offset);

	    return utc.getTime();
	}
}
