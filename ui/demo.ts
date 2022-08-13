function api<T>(url: string, method: string = "GET", headers: Headers | undefined = undefined, body: any = undefined): Promise<T> {
  const requestOptions: RequestInit = {
      method: method,
      headers: headers,
      body: JSON.stringify(body),
      redirect: 'follow'
  }
  return fetch(url, requestOptions)
    .then(response => {
      if (!response.ok) {
        throw new Error(response.statusText)
      }
      return response.json() as Promise<T>
    })
}

class RoomSearchRequest {
    startDate: string;
    endDate: string;
    capacity?: number;
    amenities?:Array<string>;
    address?: {
        city?: string;
        addressLine?: string;
        floor?: number;
    };

    constructor(startDate: string, endDate: string, capacity: number | undefined = undefined, amenities: Array<string> | undefined = undefined, address: any | undefined = undefined) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.amenities = amenities;
        this.address = address;
    }

}

class RoomResponse {
    id: number;
    name: string;
    capacity: number
    address: {
        city: string;
        addressLine: string;
        floor: number;
    }
    description?: string;
    amenities?: Array<string>;

    constructor(id: number, name: string, capacity: number, address: any, description: string | undefined = undefined, amenitites: Array<string> | undefined = undefined) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.address = address;
        this.description = description;
        this.amenities = amenitites;
    }
}


const baseUrl = "http://localhost:8080/v1";
const myHeaders = new Headers();
const username = "test";
const password = "P@$$word";
myHeaders.append("Authorization", "Basic " + btoa(username + ":" + password));
myHeaders.append("Content-Type", "application/json");

const startDate = "2022-08-13 14:30";
const endDate = "2022-08-13 14:40";
const capacity = 6;
const amenities = ["tv"];
const address = {
    "floor": 3,
    "city": "city1"
};

// Get rooms list api
const body = new RoomSearchRequest(startDate, endDate, capacity, amenities, address);
api<Array<RoomResponse>>(baseUrl + "/rooms", "POST", myHeaders, body)
    .then(rooms => console.log(rooms) /* TODO success response handling */)
    .catch(err => console.error(err) /* TODO error response handling*/);

// Book room api
const roomId = 1;
const url = baseUrl + "/rooms/" + roomId + "?startDate=" + startDate + "&endDate=" + endDate;
api<any>(url, "GET", myHeaders)
    .then(res => console.log("successfully booked room with id ", roomId) /* TODO success response handling*/)
    .catch(err => console.error(err) /* TODO error response handling*/);

