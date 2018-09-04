let dayToday;
let classDate = document.getElementsByClassName("input_date"); // returns array
// of elements
// with
// specified
// class

document.addEventListener("DOMContentLoaded", setDates);
document.addEventListener("DOMContentLoaded", inputDates);
document.addEventListener("DOMContentLoaded", loadValidation);
document.getElementById("apiPopup").addEventListener("click", showApi);

function setDates() { // With this function I'll set the min and max date
	// value to prevent user from providing invalid values.
	// This function will triger when the DOM is loaded
	let today = new Date();
	let dd = today.getDate();
	let mm = today.getMonth() + 1; // because it counts month from 0
	let yyyy = today.getFullYear();

	if (dd < 10) {
		dd = '0' + dd; // 01, 02 ... 09 - one digit vaules need to be in this
		// form for html to properly read min values that JS is
		// setting onload
	}
	if (mm < 10) {
		mm = '0' + mm;
	}

	let thisday = yyyy + "-" + mm + "-" + dd;

	return dayToday = thisday;
}

function inputDates() {
	document.getElementById("startDate").min = dayToday;
	document.getElementById("endDate").min = dayToday;
	document.getElementById("startDate").value = dayToday;
	document.getElementById("endDate").value = dayToday;
	return;
}

function compareDates() {
	let start_date = document.getElementById("startDate");
	let end_date = document.getElementById("endDate");

	if (start_date.value > end_date.value) {
		// alert("Booking START date can not be later than booking end date");
		end_date.value = start_date.value;
		// for Java version of app I set to auto-correct END date if START > END
		// then END = START - it feels more user friendly
	}
	return;
}

// adding on change eventlistner for each element with specified class
function loadValidation() {
	for (let i = 0; i < classDate.length; i++) {              ("Line 1\nLine 2")
		classDate[i].addEventListener("change", compareDates);
	}
}

function showApi() {
	alert(".../api/books/  -  will get rservations for all parking spots\n"
			+ ".../api/books/id  -  where id is number from 1 to 35 will get reservations only for particular id\n"
			+ "all data will be returned in JSON format");
}