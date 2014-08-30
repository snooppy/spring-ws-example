package ua.cn.dmitrykrivenko.springwscityexample;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author Dmitry Krivenko <dmitrykrivenko@gmail.com>
 */
@Document(collection = "cities")
public class City {

	@Id
	private String id;
	private String city1;
	private String city2;
	private int distanceBetween;

	public City() {
	}

	public City(String city1, String city2, int distanceBetween) {
		this.city1 = city1;
		this.city2 = city2;
		this.distanceBetween = distanceBetween;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *        the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the city1
	 */
	public String getCity1() {
		return city1;
	}

	/**
	 * @param city1
	 *        the city1 to set
	 */
	public void setCity1(String city1) {
		this.city1 = city1;
	}

	/**
	 * @return the city2
	 */
	public String getCity2() {
		return city2;
	}

	/**
	 * @param city2
	 *        the city2 to set
	 */
	public void setCity2(String city2) {
		this.city2 = city2;
	}

	/**
	 * @return the distanceBetween
	 */
	public int getDistanceBetween() {
		return distanceBetween;
	}

	/**
	 * @param distanceBetween
	 *        the distanceBetween to set
	 */
	public void setDistanceBetween(int distanceBetween) {
		this.distanceBetween = distanceBetween;
	}

	@Override
	public String toString() {
		return String.format("City[id =%s; city1 =%s; city2=%s; distance=%d]", id, city1, city2, distanceBetween);
	}

}
