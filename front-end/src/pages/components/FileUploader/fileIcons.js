
const defaultFileIcon = "file-icon-default.svg";

const fileExtIconsMap = new Map([
  ["xls", "file-icon-xls.svg"],
  ["xlsx", "file-icon-xls.svg"],
  ["csv", "file-icon-csv.svg"],
  ["", defaultFileIcon]
]);


function getFileExtension(fileName) {
  return fileName.slice((fileName.lastIndexOf(".") - 1 >>> 0) + 2);
}


export function getFileIcon(file) {
  if (file) {
    const fileExt = getFileExtension(file.name);
    return fileExtIconsMap.get(fileExt);
  } else {
    return defaultFileIcon;
  }
}
