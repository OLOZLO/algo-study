const solution = (files) => {
    var answer = [];

    let list = [];
    for (let i = 0; i < files.length; i++) {
        list.push(new File(files[i].toUpperCase(), i));
    }

    list.sort(compare);

    for (let i = 0; i < list.length; i++) {
        answer.push(files[list[i].index]);
    }

    return answer;
};

const compare = (f1, f2) => {
    let f1_head = makeHead(f1);
    let f2_head = makeHead(f2);

    let f1_idx = f1_head.length,
        f2_idx = f2_head.length;

    if (f1_head.localeCompare(f2_head) > 0) return 1;
    else if (f1_head.localeCompare(f2_head) < 0) return -1;
    else {
        let f1_number = makeNumber(f1, f1_idx);
        let f2_number = makeNumber(f2, f2_idx);

        if (f1_number > f2_number) return 1;
        else if (f1_number < f2_number) return -1;
        else return 0;
    }
};

const makeHead = (file) => {
    let ret = "";

    for (let i = 0; i < file.fileName.length; i++) {
        let ch = file.fileName.charAt(i);
        if (!isNaN(parseInt(ch))) break;
        ret = ret.concat(ch);
    }

    return ret;
};

const makeNumber = (file, index) => {
    let ret = "";

    while (index < file.fileName.length && !isNaN(file.fileName.charAt(index))) {
        ret = ret.concat(file.fileName.charAt(index));
        index++;
    }

    return parseInt(ret);
};

class File {
    constructor(fileName, index) {
        this.fileName = fileName;
        this.index = index;
    }
}

solution(["img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG"]);
// solution(["F-5 Freedom Fighter", "B-50 Superfortress", "A-10 Thunderbolt II", "F-14 Tomcat"]);
// solution(["img-.0005.png", "im.g-5.png", "img.- 3.jpg", "im.g- 001.png"]);
