import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'username',
})
export class UsernamePipe implements PipeTransform {
  transform(value: string | null): string | null {
    if (!value) {
      return null;
    }
    return '@' + value.toLowerCase();
  }
}
