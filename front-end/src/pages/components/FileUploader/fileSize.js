export function formatFileSize(bytes, decimalPoint, si) {
  if (bytes == 0) {
    return si ? '0 B' : '0 iB';
  }

  const k = si ? 1000 : 1024;

  const byteUnits = si
    ? ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
    : ['iB', 'KiB', 'MiB', 'GiB', 'TiB', 'PiB', 'EiB', 'ZiB', 'YiB'];

  const i = Math.floor(Math.log(bytes) / Math.log(k));
  const dm = decimalPoint || 2;

  return parseFloat((bytes / Math.pow(k, i)).toFixed(dm))
    + ' '
    + byteUnits[i];
}
